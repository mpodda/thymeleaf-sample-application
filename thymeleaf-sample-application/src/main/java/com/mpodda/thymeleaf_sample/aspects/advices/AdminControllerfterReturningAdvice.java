package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.administration.AdminController;

@Component
public class AdminControllerfterReturningAdvice implements AfterReturningAdvice {

	@SuppressWarnings("null")
	@Override
	public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) throws Throwable {
		if (AdviceUtils.isAdminController(method)) {
			Model model = AdviceUtils.locateModel(args);
			
			if (model == null) {
				return;
			}
			
			final Class<?> adminControllerClass = method.getDeclaringClass();
		
			final String sessionAttribute = adminControllerClass.getAnnotation(AdminController.class).sessionAttribute();
			final String reference = adminControllerClass.getAnnotation(AdminController.class).reference();
			
			model.addAttribute("sessionAttribute", sessionAttribute);
			model.addAttribute("reference", reference);
		}
	}
}
