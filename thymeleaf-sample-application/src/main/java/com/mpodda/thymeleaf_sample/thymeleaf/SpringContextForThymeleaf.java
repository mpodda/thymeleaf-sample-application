package com.mpodda.thymeleaf_sample.thymeleaf;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SpringContextForThymeleaf implements ApplicationContextAware {
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public static <T> T getBean(@NonNull Class<T> beanClass) {
        return context.getBean(beanClass);
    }	
}
