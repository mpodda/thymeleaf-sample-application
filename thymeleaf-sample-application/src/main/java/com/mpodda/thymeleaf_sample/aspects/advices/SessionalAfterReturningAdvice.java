package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;
import com.mpodda.thymeleaf_sample.web.PagingAndSortingService;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionalAfterReturningAdvice<Dto extends BaseDto> implements AfterReturningAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionalAfterReturningAdvice.class);
	
	@Value("${thysa.page-size}")
	private int pageSize; 

	@SuppressWarnings("rawtypes")
	@Autowired
    private FindByIndexNameSessionRepository sessionRepository;
	
//	@Autowired
//	private JdbcIndexedSessionRepository sessionRepository;
//	private SessionRepository<?> sessionRepository;
	
	@Autowired
	private PagingAndSortingService<Dto> pagingAndSortingService;

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target)throws Throwable {
		if (AdviceUtils.isSessionalMethod(method)) {
			Model model = AdviceUtils.locateModel(args); 
			HttpSession httpSession = AdviceUtils.locateHttpSession(args);
			
			if (model == null || httpSession == null) {
				return;
			}
			
			final String[] sessionAttributeNames = method.getAnnotation(SessionalMethod.class).sessionAttributeNames();
			
			/* Map that be stored in this session */
			Map<String, List<Dto>> sessionMap = null;
			
			/* Map that should be stored in this Model */
			Map<String, PagingSortingAndFilteringDto> modelMap = (Map<String, PagingSortingAndFilteringDto>)model.getAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value());
		
			LOGGER.info("Try to get Session from repository with id: {}", httpSession.getId());
			
			Session session = this.sessionRepository.findById(RequestContextHolder.currentRequestAttributes().getSessionId());
			
			if (session == null) {
				session = this.sessionRepository.createSession();
				LOGGER.info("No session found. New one created with id: {}", session.getId());
				
				sessionMap = new HashMap<String, List<Dto>>();
				
				//TODO: Improve later when Spring Security is introduced. Find other sessions in repository by principal and delete them.
			} else {
				sessionMap = (Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value());
				
				if (sessionMap == null) {
					sessionMap = new HashMap<String, List<Dto>>();
				}
			}
			
			final Method[] declaringClassMethods = method.getDeclaringClass().getMethods();
			
			for (int i=0; i</*method.getDeclaringClass().getMethods()*/declaringClassMethods.length; i++) {
				final boolean isSessionalDtoMethod = method.getDeclaringClass().getMethods()[i].getAnnotation(SessionalDto.class) != null;
				
				/* Method is 'Sessional' Dto method (Usually 'getter') */
				if (isSessionalDtoMethod) {
					/* Session Attribute Name */
					final String sessionAttributeName = /*method.getDeclaringClass().getMethods()*/declaringClassMethods[i].getAnnotation(SessionalDto.class).sessionAttributeName();
					
					/* Attribute belongs to given 'Sessional' method */
					final boolean isSessionalAttributeOfThisSessionalMethod = Arrays.stream(sessionAttributeNames).anyMatch(s -> s.equals(sessionAttributeName));					
					
					if (isSessionalAttributeOfThisSessionalMethod) {
						final Method sessionalDtoMethod = /*method.getDeclaringClass().getMethods()*/declaringClassMethods[i];
						
						try {
							/* Get Data */
							List<Dto> data = (List<Dto>)sessionalDtoMethod.invoke(target);
							
							/* Are data should be paged ? */
							final boolean paging = /*method.getDeclaringClass().getMethods()*/declaringClassMethods[i].getAnnotation(SessionalDto.class).paging();
							
							/* Paging case */
							if (paging) {
								PagingSortingAndFilteringDto pagingAndSortingDto = new PagingSortingAndFilteringDto();
								pagingAndSortingDto = this.pagingAndSortingService.pagingSetup(pagingAndSortingDto, data);
								modelMap.put(sessionAttributeName, pagingAndSortingDto);
								model.addAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value(), modelMap);
								
								model.addAttribute(sessionAttributeName, pagingAndSortingDto.getPageData());
							} else {
								/* No Paging case */
								model.addAttribute(sessionAttributeName, data);	
							}
							
							sessionMap.put(sessionAttributeName, data);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if (!sessionMap.isEmpty()) {
				session.setAttribute(SessionalConstants.DATA.value(), sessionMap);
				this.sessionRepository.save(session);
			}			
		}
	}
}
