package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;

import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;
import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;

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
	
	public static boolean isFilterialPreservation(final Method method) {
		return method.getAnnotation(FilterialPreservations.class) != null;
	}
}
