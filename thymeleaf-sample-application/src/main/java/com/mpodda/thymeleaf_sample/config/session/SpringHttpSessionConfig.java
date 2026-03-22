package com.mpodda.thymeleaf_sample.config.session;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableSpringHttpSession
@EnableJdbcHttpSession
@Configuration
public class SpringHttpSessionConfig {
	@Bean
	MapSessionRepository mapSessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}
	
	
}
