package com.mpodda.thymeleaf_sample.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;
import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

@Aspect
@Component
public class PersisterialPreservationAspectService <Dto extends BaseDto> {

	@Pointcut("within(com.mpodda.thymeleaf_sample.controllers.pages..*)")
	private void anyPageControllerExecution() {
		
	}

	//public String saveCountry(@Validated @ModelAttribute CountryDto countryDto, Errors errors, Model model, HttpServletResponse response) {
	@After ("anyPageControllerExecution()  && args(modelAttribute, errors, model,..)")
	public void afterPageControllerExecution(JoinPoint joinPoint, @ModelAttribute Dto modelAttribute, Errors errors, Model model) {
//		System.out.println("afterPageControllerExecution");
		
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		final boolean isPersisterialPreservation = (methodSignature.getMethod().getAnnotation(PersisterialMethod.class) != null);
		
		if (isPersisterialPreservation) {
			final String modelAttributeName = methodSignature.getMethod().getAnnotation(PersisterialMethod.class).preservedModelAttributeName();
			
			model.addAttribute(modelAttributeName, modelAttribute);
		}
	}
	
}
