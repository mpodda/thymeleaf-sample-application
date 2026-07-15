package com.mpodda.thymeleaf_sample.session;

import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Service;

//@Service
public class JdbcSessionService {
	private JdbcIndexedSessionRepository sessionRepository;

	public JdbcSessionService(JdbcIndexedSessionRepository sessionRepository) {
		super();
		this.sessionRepository = sessionRepository;
	}

	public void clearAttribute(String sessionId, String attributeName) {
		var session = sessionRepository.findById(sessionId);
		
		
		
        if (session != null) {
        	 //session.removeAttribute(attributeName);
        	 
            this.sessionRepository.save(session);
        }
    }	
}
