package com.mpodda.thymeleaf_sample.aspects;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;
import com.mpodda.thymeleaf_sample.web.PagingAndSortingService;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class SessionalAspectService <Dto extends BaseDto> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionalAspectService.class);
	
	@Value("${tsa.page-size}")
	private int pageSize; 

	
	@SuppressWarnings("rawtypes")
	@Autowired
    private FindByIndexNameSessionRepository sessionRepository;
	
	@Autowired
	private PagingAndSortingService<Dto> pagingAndSortingService;
	
	
	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}
	
	@SuppressWarnings({"unchecked", "null"})
	@After ("anyPageControllerExecution()  && args(model, httpSession,..)")
	public void afterPageControllerExecution(JoinPoint joinPoint, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		/* Is 'Sessional' Controller Method */
		final boolean isSessionalMethod = (methodSignature.getMethod().getAnnotation(SessionalMethod.class) != null);
		
		if (isSessionalMethod) {
			final String[] sessionAttributeNames = methodSignature.getMethod().getAnnotation(SessionalMethod.class).sessionAttributeNames();
			
			/* Map that be stored in this session */
			Map<String, List<Dto>> sessionMap = new HashMap<String, List<Dto>>();
			
			/* Map that should be stored in this Model */
			Map<String, PagingAndSortingDto> modelMap = (Map<String, PagingAndSortingDto>)model.getAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value());
		
			LOGGER.info("Try to get Session from repository with id: {}", httpSession.getId());
			
			Session session = this.sessionRepository.findById(RequestContextHolder.currentRequestAttributes().getSessionId());
			
			if (session == null) {
				session = this.sessionRepository.createSession();
				LOGGER.info("No session found. New one created with id: {}", session.getId());
				
				//TODO: Improve later when Spring Security is introduced. Find other sessions in repository by principal and delete them.
			}
			
			for (int i=0; i<methodSignature.getDeclaringType().getMethods().length; i++) {
				final boolean isSessionalDtoMethod = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class) != null;
				
				/* Method is 'Sessional' Dto method (Usually 'getter') */
				if (isSessionalDtoMethod) {
					/* Session Attribute Name */
					final String sessionAttributeName = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class).sessionAttributeName();
					
					/* Attribute belongs to given 'Sessional' method */
					final boolean isSessionalAttributeOfThisSessionalMethod = Arrays.stream(sessionAttributeNames).anyMatch(s -> s.equals(sessionAttributeName));					
					
					if (isSessionalAttributeOfThisSessionalMethod) {
						final Method method = methodSignature.getDeclaringType().getMethods()[i];
						
						try {
							/* Get Data */
							List<Dto> data = (List<Dto>)method.invoke(joinPoint.getTarget());
							
							/* Are data should be paged ? */
							final boolean paging = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class).paging();
							
							/* Paging case */
							if (paging) {
								PagingAndSortingDto pagingAndSortingDto = new PagingAndSortingDto();
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
