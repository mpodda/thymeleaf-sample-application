package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;

import jakarta.servlet.http.HttpSession;

@Controller
public class PersonsController extends BaseController {
	
	
	@Override
	protected void addValidators(WebDataBinder binder) {
		
	}

	@SessionalDto(sessionAttributeName = "persons")
	public List<PersonDto> getPersonsList() {
		return null;
	}
	
	@SessionalMethod(sessionAttributeNames = {"persons"})
	@GetMapping({"/persons"})
	public String persons(Model model, HttpSession httpSession) {
		return "application/persons";
	}
	
	@GetMapping({"/new-person"})
	public String newContinent(Model model) {
		return "application/fragments/persons-fragments :: edit-person";
	}
	
	@SessionalMethod(sessionAttributeNames = {"persons"})
	@GetMapping({"/list-persons"})
	public String listPesons( Model model, HttpSession httpSession) {
		return "application/fragments/persons-fragments :: persons-list";
	}	
	
}
