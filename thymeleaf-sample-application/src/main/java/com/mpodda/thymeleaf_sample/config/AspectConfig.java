package com.mpodda.thymeleaf_sample.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;

import com.mpodda.thymeleaf_sample.aspects.SessionalAspectService;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

//	@SuppressWarnings("rawtypes")
//	@Bean
//	Advisor SessionalAspectServiceAdvisor (@Value("${aspect.sessional-aspect-service.pointcut}") String expression, @NonNull  SessionalAspectService<?> advice) {
//	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//	    pointcut.setExpression(expression);
//	
//	    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
//	    defaultPointcutAdvisor.setPointcut(pointcut);
//	    defaultPointcutAdvisor.setAdvice(advice);
//		
//	    return defaultPointcutAdvisor;
//	}
}
