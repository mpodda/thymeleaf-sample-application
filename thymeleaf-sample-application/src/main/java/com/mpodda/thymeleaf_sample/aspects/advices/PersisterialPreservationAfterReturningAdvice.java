package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

import jakarta.servlet.http.HttpSession;

@Component
public class PersisterialPreservationAfterReturningAdvice <Dto extends BaseDto> implements AfterReturningAdvice {

	@SuppressWarnings("null")
	@Override
	public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) throws Throwable {
		if (AdviceUtils.isPersisterialPreservation(method)) {
			Model model = AdviceUtils.locateModel(args);
			HttpSession httpSession = AdviceUtils.locateHttpSession(args);
			
			if (model == null || httpSession == null) {
				return;
			}
			
			final String preservedObjectModelAttributeName = method.getAnnotation(PersisterialMethod.class).preservedObjectModelAttributeName();
			
			if (preservedObjectModelAttributeName != null && !preservedObjectModelAttributeName.isBlank()) {
				for (Object arg : args) {
					if (arg instanceof BaseDto) {
						model.addAttribute(preservedObjectModelAttributeName, arg);
					}
				}
			}
			
			Arrays.stream(method.getAnnotation(PersisterialMethod.class).preservedModelAttributeNames()).forEach (
				preservedModelAttributeName -> model.addAttribute(preservedModelAttributeName, httpSession.getAttribute(preservedModelAttributeName))
			);			
		}
	}
}
