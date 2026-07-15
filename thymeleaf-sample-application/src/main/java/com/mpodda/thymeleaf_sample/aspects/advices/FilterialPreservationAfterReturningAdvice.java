package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;

import jakarta.servlet.http.HttpSession;

@Component
public class FilterialPreservationAfterReturningAdvice implements AfterReturningAdvice {

	@SuppressWarnings("null")
	@Override
	public void afterReturning(@Nullable Object returnValue, @NonNull Method method, Object[] args, @Nullable Object target) throws Throwable {
		if (AdviceUtils.isFilterialPreservation(method)) {
			Model model = AdviceUtils.locateModel(args); 
			HttpSession httpSession = AdviceUtils.locateHttpSession(args);
			
			if (model == null || httpSession == null) {
				return;
			}
			
			final String[] modelAttributeNames = method.getAnnotation(FilterialPreservations.class).modelAttributeNames();
			
			Arrays.stream(modelAttributeNames).forEach(m -> {
				httpSession.setAttribute(m, model.getAttribute(m));
			});
		}
	}
}
