package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;

import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminController;

import jakarta.servlet.http.HttpSession;

public final class AdviceUtils {
	public static Model locateModel(Object[] args) {
		for (Object arg : args) {
			if (arg instanceof Model model) {
				return model;
			}
		}
		
		return null;
	}
	
	public static HttpSession locateHttpSession(Object[] args) {
		for (Object arg : args) {
			if (arg instanceof HttpSession httpSession) {
				return httpSession;
			}
		}
		
		return null;
	}

	public static boolean isSessionalMethod(final Method method) {
		return method.getAnnotation(SessionalMethod.class) != null;
	}
	
	public static boolean isSelectialMethod(final Method method) {
		return method.getAnnotation(SelectialMethod.class) != null;
	}
	
	public static boolean isPersisterialPreservation(final Method method) {
		return method.getAnnotation(PersisterialMethod.class) != null;
	}
	
	public static boolean isAdminController(final Method method) {
		return method.getDeclaringClass().getAnnotation(AdminController.class) != null;
	}
}


