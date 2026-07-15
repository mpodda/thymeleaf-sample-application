package com.mpodda.thymeleaf_sample.config.session;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import com.zaxxer.hikari.HikariDataSource;

@EnableSpringHttpSession
@EnableJdbcHttpSession(tableName = "thysa_spring_session")
@Configuration
public class SpringHttpSessionConfig {
	
	@Bean
	MapSessionRepository mapSessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	HikariDataSource applicationDataSource() {
		return DataSourceBuilder.create()
	            .type(HikariDataSource.class)
	            .build();
	}
	
//	@Bean
//	@SpringSessionDataSource
//	@ConfigurationProperties(prefix = "app.session.datasource")
//	HikariDataSource springSessionDataSource() {
//		/*
//		return DataSourceBuilder.create()
//	            .type(HikariDataSource.class)
//	            .build();
//	            */
//		 return new HikariDataSource();
//	}

	
	@Bean
    @ConfigurationProperties("app.session.datasource")
    DataSourceProperties sessionDataSourceProperties() {
        return new DataSourceProperties();
    }
	
	
	@Bean
    @SpringSessionDataSource
    DataSource springSessionDataSource() {
        return sessionDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
	
//	@Bean
//	JdbcSessionDataSourceScriptDatabaseInitializer jdbcSessionInitializer(DataSource dataSource, JdbcSessionProperties properties) {
//	    return new JdbcSessionDataSourceScriptDatabaseInitializer(dataSource, properties);
//	}
}
