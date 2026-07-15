package com.mpodda.thymeleaf_sample.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.converters.ContinentDtoConverter;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;

import jakarta.servlet.http.HttpSession;

//@Aspect
//@Component
public class SelectialPreservationAspectService {
	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}

	
	/**
	 * @param joinPoint
	 * @param model
	 * @param httpSession
	 */
	@SuppressWarnings("null")
	@After ("anyPageControllerExecution()  && args(model, httpSession,..)")
	public void afterPageControllerExecution(JoinPoint joinPoint, Model model, HttpSession httpSession) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		final boolean isSelectialMethod = (methodSignature.getMethod().getAnnotation(SelectialMethod.class) != null);
		
		if (isSelectialMethod) {
			
			System.out.println("SelectialPreservationAspectService :: afterPageControllerExecution");
			
			final String[] sessionAttributeNames = methodSignature.getMethod().getAnnotation(SelectialMethod.class).preservedSessionAttributeNames();
			
			Arrays.stream(sessionAttributeNames).forEach(sessionAttribute -> {
				httpSession.setAttribute(sessionAttribute, model.getAttribute(sessionAttribute));
			});
			
			final String[] modelAttributeNames = methodSignature.getMethod().getAnnotation(SelectialMethod.class).preservedModelAttributeNames();
			
			Arrays.stream(modelAttributeNames).forEach(modelAttribute -> {
				model.addAttribute(modelAttribute, httpSession.getAttribute(modelAttribute));
			});
			
			if (model.getAttribute("onValueChangeDto") != null) {
				final OnValueChangeDto onValueChangeDto = (OnValueChangeDto)model.getAttribute("onValueChangeDto");
				model.addAttribute("randomSuffix", onValueChangeDto.getRandomSuffix());
			}
		}
	}
}
