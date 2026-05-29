package com.mpodda.thymeleaf_sample.thymeleaf;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.thymeleaf.context.IContext;

public final class ValidationUtils {
	
	@SuppressWarnings("unchecked")
	public static boolean fieldIsInvalid(IContext context, final String fieldName) {
		List<FieldError> fieldErrorsList = (List<FieldError>)context.getVariable("fieldErrors");
		
		if (fieldErrorsList != null && !fieldErrorsList.isEmpty())  {
			return fieldErrorsList.stream().filter(fieldError -> fieldError.getField().equals(fieldName)).count() > 0;
		}
		
		return false;
	}
	
	private static String getMessage(final @NonNull String code) {
		return SpringContextForThymeleaf.getBean(MessageSource.class).getMessage(code, null, LocaleContextHolder.getLocale());
	}
	
	private static String getMessage(final @NonNull String code, Object[] arguments) {
		return SpringContextForThymeleaf.getBean(MessageSource.class).getMessage(code, arguments, LocaleContextHolder.getLocale());	
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	public static String errorMessage(IContext context, final String fieldName) {
		List<FieldError> fieldErrorsList = (List<FieldError>)context.getVariable("fieldErrors");
		
		if (fieldErrorsList != null && !fieldErrorsList.isEmpty())  {
			for (FieldError fieldError : fieldErrorsList) {
				if (fieldError.getField().equals(fieldName)) {
					if (fieldError.getArguments() == null) {
						return getMessage (fieldError.getCode());	
					}
					
					return getMessage(fieldError.getCode(), fieldError.getArguments());
				}
			}
		}
		
		return "";
	}
}
