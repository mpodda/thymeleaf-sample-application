package com.mpodda.thymeleaf_sample.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;

import com.mpodda.thymeleaf_sample.aspects.advices.AdminControllerfterReturningAdvice;
import com.mpodda.thymeleaf_sample.aspects.advices.PersisterialPreservationAfterReturningAdvice;
import com.mpodda.thymeleaf_sample.aspects.advices.SelectialPreservationAfterReturningAdvice;
import com.mpodda.thymeleaf_sample.aspects.advices.SessionalAfterReturningAdvice;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

	@Bean
	Advisor SessionalAspectServiceAdvisor (@Value("${aspect.page-controllers.pointcut}") String expression, @NonNull SessionalAfterReturningAdvice<?> advice) {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression(expression);
	
	    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
	    defaultPointcutAdvisor.setPointcut(pointcut);
	    defaultPointcutAdvisor.setAdvice(advice);
		
	    return defaultPointcutAdvisor;
	}
	
	@Bean
	Advisor SelectialPreservationAspectServiceAdvisor (@Value("${aspect.page-controllers.pointcut}") String expression, @NonNull SelectialPreservationAfterReturningAdvice advice) {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression(expression);
	
	    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
	    defaultPointcutAdvisor.setPointcut(pointcut);
	    defaultPointcutAdvisor.setAdvice(advice);
		
	    return defaultPointcutAdvisor;
	}
	
	@Bean
	Advisor PersisterialPreservationAspectServiceAdvisor (@Value("${aspect.page-controllers.pointcut}") String expression, @NonNull PersisterialPreservationAfterReturningAdvice<?> advice) {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression(expression);
	
	    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
	    defaultPointcutAdvisor.setPointcut(pointcut);
	    defaultPointcutAdvisor.setAdvice(advice);
		
	    return defaultPointcutAdvisor;
	}
	
	@Bean
	Advisor AdminControllerAspectServiceAdvisor (@Value("${aspect.page-controllers.pointcut}") String expression, @NonNull AdminControllerfterReturningAdvice advice) {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression(expression);
	
	    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
	    defaultPointcutAdvisor.setPointcut(pointcut);
	    defaultPointcutAdvisor.setAdvice(advice);
		
	    return defaultPointcutAdvisor;
	}
}
