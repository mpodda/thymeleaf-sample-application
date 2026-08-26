package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.service.CountryService;
import com.mpodda.thymeleaf_sample.service.PersonService;
import com.mpodda.thymeleaf_sample.validators.PersonDtoValidator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PersonsController extends BaseController {
	private PersonService personService;
	private ContinentService continentService;
	private CountryService countryService;
	
	private PersonDtoValidator personDtoValidator;

	public PersonsController(PersonService personService, ContinentService continentService,
			CountryService countryService, PersonDtoValidator personDtoValidator) {
		this.personService = personService;
		this.continentService = continentService;
		this.countryService = countryService;
		this.personDtoValidator = personDtoValidator;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {
		binder.addValidators(this.personDtoValidator);
	}

	@SessionalDto(sessionAttributeName = "persons")
	public List<PersonDto> getPersonsList() {
		return this.personService.allDto();
	}
	
	@SessionalMethod(sessionAttributeNames = {"persons"})
	@GetMapping({"/persons"})
	public String persons(Model model, HttpSession httpSession) {
		return "application/persons";
	}
	
	@SelectialMethod(preservedSessionAttributeNames = {"object", "filteredContinents", "filteredCountries"})
	@GetMapping({"/new-person"})
	public String newContinent(Model model, HttpSession httpSession, HttpServletResponse response) {
		model.addAttribute("object", this.personService.dtoDefaultInstance());
		
		final List<ContinentDto> continentsList = continentService.allDto();
		model.addAttribute("filteredContinents", continentsList);
		
		try {
			model.addAttribute("filteredCountries", continentsList.isEmpty() ? new ArrayList<CountryDto>(0) :  this.countryService.filterByContinent(continentsList.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/persons-fragments :: edit-person";
	}
	
	@PersisterialMethod(preservedObjectModelAttributeName = "object", preservedModelAttributeNames= {"filteredContinents", "filteredCountries"})
	@Transactional
	@PostMapping({"/save-person"})
	public String savePerson(@Validated @ModelAttribute PersonDto modelAttribute, Errors errors, Model model, HttpSession httpSession, HttpServletResponse response) {
		if (!errors.getFieldErrors().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			model.addAttribute("fieldErrors", errors.getFieldErrors());
		} else {
			try {
				modelAttribute = this.personService.saveFromDto(modelAttribute);
			} catch (Exception e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				e.printStackTrace();
			}
			response.setStatus(HttpStatus.OK.value());
		}

		model.addAttribute("object", modelAttribute);
		
		return "application/fragments/persons-fragments :: edit-person";
	}
	
	@SessionalMethod(sessionAttributeNames = {"persons"})
	@GetMapping({"/list-persons"})
	public String listPesons(Model model, HttpSession httpSession) {
		return "application/fragments/persons-fragments :: persons-list";
	}	
	

}
