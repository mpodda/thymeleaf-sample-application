package com.mpodda.thymeleaf_sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.entities.Continent;

import jakarta.annotation.PostConstruct;

@Service
public class ContinentFilterService  {
	private List<Continent> continentsList = null;
	
	private ContinentService continentService;
	
	public ContinentFilterService(ContinentService continentService) {
		this.continentService = continentService;
	}

	@PostConstruct
	private void init() {
		this.refresh();
	}
	
	public void refresh() {
		this.continentsList = this.continentService.findAll();
	}
	
	public List<Continent> filterByName(final String name) {
//		if (name.isBlank()) {
//			return new ArrayList<Continent>(0);
//		}
		
		//System.out.println(String.format("name: '%s', results: %s", name, this.continentsList.stream().filter(c -> c.getName().contains(name)).toList().size()));
		
		return this.continentsList.stream().filter(c -> c.getName().contains(name)).toList();
	}
}
