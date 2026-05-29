package com.mpodda.thymeleaf_sample.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18nConfig {
	
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//		resourceBundleMessageSource.setBasename("classpath:i18n/messages");
		resourceBundleMessageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/texts");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		
		resourceBundleMessageSource.setDefaultLocale(Locale.ENGLISH);
		
		return resourceBundleMessageSource;
	}
	
	/*
	@Bean
	MessageSource textMessageSourceSource() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:i18n/texts");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");

		resourceBundleMessageSource.setDefaultLocale(Locale.ENGLISH);
		
		return resourceBundleMessageSource;
	}
	*/
}
