package com.mpodda.thymeleaf_sample.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Repository;

//@Repository
public class JDBSessionRepository implements SessionRepository<Session> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Session createSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Session findById(String id) {
		// TODO Auto-generated method stub
		 
		return null;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

}
