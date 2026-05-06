package com.mpodda.thymeleaf_sample.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;

@Controller
public class ContinentsController {
	
	@GetMapping({"/new-continent"})
	public String newContinent(Model model) {
		model.addAttribute("continent", ContinentDto.newInstance());
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
	@GetMapping({"/edit-continent"})
	public String editContinent(@RequestParam (required = false) Long continentId, Model model) {
		model.addAttribute("continent", ContinentDto.newInstance());
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
}
