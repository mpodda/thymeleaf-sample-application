package com.mpodda.thymeleaf_sample.thymeleaf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IEngineContext;
import org.thymeleaf.context.IWebContext;

import com.mpodda.thymeleaf_sample.annotations.administration.AddValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminController;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminIdParameter;
import com.mpodda.thymeleaf_sample.annotations.administration.EditValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.ListValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.SaveValueMethod;

public final class JavascriptUtils {
	public static String adminJavascript(IContext context) {
		IEngineContext engineContext = (IEngineContext)context;
		IWebContext webContext = (IWebContext)context;
		
		final RequestMappingHandlerMapping handlerMapping = SpringContextForThymeleaf.getBean(RequestMappingHandlerMapping.class);
		
		final Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
		
		Method[] adminControllerMethods = {};
		Class<?> adminControllerClass = null;
		
		
		/* Find Admin Controller */
		for (RequestMappingInfo mapping : mappings.keySet()) {
			final HandlerMethod handlerMethod = mappings.get(mapping);
			
			if (mapping.getDirectPaths().contains(webContext.getExchange().getRequest().getRequestPath())) {
				if (handlerMethod.getMethod().getDeclaringClass().getAnnotation(AdminController.class) != null) {
					adminControllerClass = handlerMethod.getMethod().getDeclaringClass();
					adminControllerMethods = handlerMethod.getMethod().getDeclaringClass().getDeclaredMethods();
					
					break;
				}
			}
			
			if (adminControllerMethods.length > 0) {
				break;
			}
		}
		
		List<Method> adminControllerMethodsList =Arrays.asList(adminControllerMethods);
		
		if (adminControllerClass != null) {
			/* Values derived directly from AdminController Class */
			
			final String sessionAttribute = adminControllerClass.getAnnotation(AdminController.class).sessionAttribute();
			
			/* Instance Class Name */
			engineContext.setVariable("instanceClassName", Character.toUpperCase(sessionAttribute.charAt(0)) + sessionAttribute.substring(1));
			
			/* Session Attribute */
			engineContext.setVariable("sessionAttribute", sessionAttribute);
			
			/* Add Value Role */
			engineContext.setVariable("addValueRole", "add-" + adminControllerClass.getAnnotation(AdminController.class).reference());
			
			/* Edit Value Role */
			engineContext.setVariable("editValueRole", "edit-" + adminControllerClass.getAnnotation(AdminController.class).reference());
			
			
			
			/* Traverse mappings to bind with AdminController Methods */
			mappings.forEach((mapping, handlerMethod) -> {
				
				/* It is about AdminController Method */
				if (adminControllerMethodsList.contains(handlerMethod.getMethod())) {
					final String directPath = mapping.getDirectPaths().iterator().hasNext() ? mapping.getDirectPaths().iterator().next() : "";
					
					/* Add Value */
					if (handlerMethod.getMethodAnnotation(AddValueMethod.class) != null) {
						engineContext.setVariable("addValueUrl", directPath);
					}
					
					/* List Value */
					if (handlerMethod.getMethodAnnotation(ListValueMethod.class) != null) {
						engineContext.setVariable("listValueUrl", directPath);
					}
					
					/* Save Value */
					if (handlerMethod.getMethodAnnotation(SaveValueMethod.class) != null) {
						engineContext.setVariable("saveValueUrl", directPath);
					}
					
					/* Edit Value */
					if (handlerMethod.getMethodAnnotation(EditValueMethod.class) != null) {
						StringBuffer editValueUrl = new StringBuffer();
						
						editValueUrl.append(directPath);
						
						for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
							if (methodParameter.getParameterAnnotation(AdminIdParameter.class) != null) {
								editValueUrl.append("?").append(methodParameter.getParameter().getName());
							}
						}
						
						engineContext.setVariable("editValueUrl", editValueUrl.toString());
					}
				}
			});
			
			TemplateEngine templateEngine = SpringContextForThymeleaf.getBean(TemplateEngine.class);
			
			return templateEngine.process("application/js/AdminInstanceTemplate.js", engineContext);
		}
		//return "console.info('Hello from JavascriptUtils.adminJavascript !!!')";
		return "";
	}
}
