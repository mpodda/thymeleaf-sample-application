package com.mpodda.thymeleaf_sample.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class PersisterialPreservationAspectService <Dto extends BaseDto> {

	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}

	@SuppressWarnings("null")
	@After ("anyPageControllerExecution()  && args(modelAttribute, errors, model, httpSession,..)")
	public void afterPageControllerExecution(JoinPoint joinPoint, @ModelAttribute Dto modelAttribute, Errors errors, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		final boolean isPersisterialPreservation = (methodSignature.getMethod().getAnnotation(PersisterialMethod.class) != null);
		
		if (isPersisterialPreservation) {
			final String preservedObjectModelAttributeName = methodSignature.getMethod().getAnnotation(PersisterialMethod.class).preservedObjectModelAttributeName();
			
			if (preservedObjectModelAttributeName != null && !preservedObjectModelAttributeName.isBlank()) {
				model.addAttribute(preservedObjectModelAttributeName, modelAttribute);
			}
			
			Arrays.stream(methodSignature.getMethod().getAnnotation(PersisterialMethod.class).preservedModelAttributeNames()).forEach (
				preservedModelAttributeName -> model.addAttribute(preservedModelAttributeName, httpSession.getAttribute(preservedModelAttributeName))
			);
		}
	}
}
