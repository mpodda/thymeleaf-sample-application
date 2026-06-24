package com.mpodda.thymeleaf_sample.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.FilterialMethod;
import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.FilterDto;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class FilterialPreservationAspectService <Dto extends BaseDto> {
	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}

	@After ("anyPageControllerExecution()  && args(model, httpSession,..)")
	public void afterPageControllerExecution(JoinPoint joinPoint, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		final boolean isFilterialPreservation = (methodSignature.getMethod().getAnnotation(FilterialPreservations.class) != null);
		
		if (isFilterialPreservation) {
			final String[] modelAttributeNames = methodSignature.getMethod().getAnnotation(FilterialPreservations.class).modelAttributeNames();
			
			Arrays.stream(modelAttributeNames).forEach(m -> {
				httpSession.setAttribute(m, model.getAttribute(m));
			});
		}
	}
	
	@Before ("anyPageControllerExecution()  && args(model, httpSession,..)")
	public void beforePageControllerExecution(JoinPoint joinPoint, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		final boolean isFilterialMethod = (methodSignature.getMethod().getAnnotation(FilterialMethod.class) != null);
		
		if (isFilterialMethod) {
			final FilterDto filterDto = (FilterDto)model.getAttribute("filterDto");
			
			if (filterDto != null) {
				model.addAttribute("randomSuffix", filterDto.getRandomSuffix());
			}
			
			final String[] modelAttributeNames = methodSignature.getMethod().getAnnotation(FilterialMethod.class).preservedModelAttributeNames();
			
			Arrays.stream(modelAttributeNames).forEach(m -> {
				model.addAttribute(m, httpSession.getAttribute(m));
			});
		}
	}
}
