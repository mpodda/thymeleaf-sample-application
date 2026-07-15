package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;

import jakarta.servlet.http.HttpSession;

@Component
public class SelectialPreservationAfterReturningAdvice implements AfterReturningAdvice {
	
	@SuppressWarnings("null")
	@Override
	public void afterReturning(@Nullable Object returnValue, @NonNull Method method, Object[] args, @Nullable Object target) throws Throwable {
		if (AdviceUtils.isSelectialMethod(method)) {
			Model model = AdviceUtils.locateModel(args); 
			HttpSession httpSession = AdviceUtils.locateHttpSession(args);
			
			if (model == null || httpSession == null) {
				return;
			}
			
			final String[] sessionAttributeNames = method.getAnnotation(SelectialMethod.class).preservedSessionAttributeNames();
			
			Arrays.stream(sessionAttributeNames).forEach(sessionAttribute -> {
				httpSession.setAttribute(sessionAttribute, model.getAttribute(sessionAttribute));
			});
			
			final String[] modelAttributeNames = method.getAnnotation(SelectialMethod.class).preservedModelAttributeNames();
			
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
