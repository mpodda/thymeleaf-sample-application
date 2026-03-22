package com.mpodda.thymeleaf_sample.aspects;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.query.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseIdentifiableDto;
import com.mpodda.thymeleaf_sample.domain.enums.PagingAndSortingConstants;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;

import jakarta.servlet.http.HttpSession;
@Aspect
@Component
public class SessionalAspectService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionalAspectService.class);
	
	@Autowired
    protected FindByIndexNameSessionRepository sessionRepository;
	
	
	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}
	
	@After ("anyPageControllerExecution()  && args(model, httpSession,..)")
	public <Dto extends BaseIdentifiableDto> void afterPageControllerExecution(JoinPoint joinPoint, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		final boolean isSessionalMethod = (methodSignature.getMethod().getAnnotation(SessionalMethod.class) != null);
		
		if (isSessionalMethod) {
			final String[] sessionAttributeNames = methodSignature.getMethod().getAnnotation(SessionalMethod.class).sessionAttributeNames();
			
			Map<String, List<? extends BaseIdentifiableDto>> map = new HashMap<String, List<? extends BaseIdentifiableDto>>();
		
			LOGGER.info("Try to get Session from repository with id: {}", httpSession.getId());
			
			Session session = this.sessionRepository.findById(RequestContextHolder.currentRequestAttributes().getSessionId());
			
			if (session == null) {
				session = this.sessionRepository.createSession();
				LOGGER.info("No session found. New one created with id: {}", session.getId());
				
				//TODO: Improve later if Spring Security is introduced. Find other sessions in repository by principal and delete them 
			}
			
			for (int i=0; i<methodSignature.getDeclaringType().getMethods().length; i++) {
				final boolean isSessionalDtoMethod = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class) != null;
				
				if (isSessionalDtoMethod) {
					final String sessionAttributeName = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class).sessionAttributeName();
					final boolean isSessionalAttributeOfThisSessionalMethod = Arrays.stream(sessionAttributeNames).anyMatch(s -> s.equals(sessionAttributeName));					
					
					if (isSessionalAttributeOfThisSessionalMethod) {
						Method method = methodSignature.getDeclaringType().getMethods()[i];
						
						try {
							//List<? extends BaseIdentifiableDto> data = (List<? extends BaseIdentifiableDto>)method.invoke(joinPoint.getTarget(), null);
							List<Dto> data = (List<Dto>)method.invoke(joinPoint.getTarget(), null);
							
							final boolean paging = methodSignature.getDeclaringType().getMethods()[i].getAnnotation(SessionalDto.class).paging();
							
							if (paging) {
								PagedListHolder<Dto> pagedListHolder = new PagedListHolder<Dto>(data);
								pagedListHolder.setPageSize(PagingAndSortingConstants.PAGE_SIZE.intValue());
								pagedListHolder.setPage(PagingAndSortingConstants.DEFAULT_PAGE.intValue());
								
								model.addAttribute(sessionAttributeName, pagedListHolder.getPageList());
							} else {
								model.addAttribute(sessionAttributeName, data);	
							}
							
							map.put(sessionAttributeName, data);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if (!map.isEmpty()) {
				session.setAttribute(SessionalConstants.DATA.value(), map);
				this.sessionRepository.save(session);
			}
		}
	}
	
//	@Pointcut("@annotation(com.mpodda.thymeleaf_sample.annotations.SessionalDto)")
//	private void sesso() {
//		System.out.println("Call sesso()");
//	}
	
//	@Around("@annotation(com.mpodda.thymeleaf_sample.annotations.SessionalDto)")
//	public void aroundSessionalDto(ProceedingJoinPoint joinPoint) {
//		System.out.println("Call aroundSessionalDto()");
//	}

//	public static void main(String[] args) {
//		System.out.println("Hello!");
//		
//		List<String> sList = new ArrayList<String>();
//		sList.add("A");sList.add("B");sList.add("C");sList.add("D");sList.add("E");
//
//		PagedListHolder<String> pagedListHolder = new PagedListHolder<String>(sList);
//		pagedListHolder.setPageSize(2);
//		pagedListHolder.setPage(0);
//		
//		System.out.println(String.format("Page 1: %s", pagedListHolder.getPageList()));
//		pagedListHolder.nextPage();
//		System.out.println(String.format("Page 2: %s", pagedListHolder.getPageList()));
//		
//		/*
//		Pageable pageable = PageRequest.of(0, 2);
//		
//		PageImpl<String> pageImpl = new PageImpl<String>(sList, pageable, 0);
//		
//		System.out.println(String.format("Content: %s", pageImpl.getContent()));
//		*/
//		
//	}
}
