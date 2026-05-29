package com.mpodda.thymeleaf_sample.controllers.pages;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.validators.ContinentDtoValidator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContinentsController extends BaseController {
	private ContinentService continentService;
	
	private List<ContinentDto> continentsList;

	private ContinentDtoValidator continentDtoValidator;
	

	public ContinentsController(ContinentService continentService, ContinentDtoValidator continentDtoValidator) {
		this.continentService = continentService;
		this.continentDtoValidator = continentDtoValidator;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {
		binder.addValidators(this.continentDtoValidator);
	}
	
	@SessionalDto(sessionAttributeName = "continents")
	public List<ContinentDto> getContinentsList() {
		return continentsList;
	}
	
	@SessionalMethod(sessionAttributeNames = {"continents"})
	@GetMapping({"/continents"})
	public String continents(Model model, HttpSession httpSession) {
		this.continentsList = this.continentService.allDto();
		
		return "application/continents";
	}
	

	@GetMapping({"/new-continent"})
	public String newContinent(Model model) {
		model.addAttribute("continent", this.continentService.dtoDefaultInstance());
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
	@GetMapping({"/edit-continent"})
	public String editContinent(@RequestParam (required = false) Long continentId, Model model, HttpServletResponse response) {
		try {
			model.addAttribute("continent", this.continentService.dtoByEntityId(continentId));
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
	@SessionalMethod(sessionAttributeNames = {"continents"})
	@GetMapping({"/list-continents"})
	public String listContinents( Model model, HttpSession httpSession) {
		this.continentsList = this.continentService.allDto();
		
		return "application/fragments/continents-fragments :: continents-list";
	}
	
	@Transactional
	@PostMapping({"/save-continent"})
	public String saveContinent(@Validated @ModelAttribute ContinentDto continentDto, Errors errors, Model model, HttpServletResponse response) {
		
		/*
		System.out.println(String.format("%s Field Errors", errors.getFieldErrors().size()));
		
		errors.getFieldErrors().forEach (
			fieldError -> {
				System.out.println(String.format("Error saving Continent: %s", getMessage(fieldError.getCode())));
				System.out.println(String.format("%s arguments : ", fieldError.getArguments().length));
				
				for (int i=0; i<fieldError.getArguments().length; i++) {
					System.out.println(String.format("%s. %s", i+1, fieldError.getArguments()[i]));
				}
			}
		);
		
		System.out.println(String.format("continentDto: %s", continentDto));
		*/
		
		
		if (!errors.getFieldErrors().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			model.addAttribute("fieldErrors", errors.getFieldErrors());
		} else {
			try {
				continentDto = this.continentService.saveFromDto(continentDto);
			} catch (Exception e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				e.printStackTrace();
			}
			
			response.setStatus(HttpStatus.OK.value());
		}
		
		model.addAttribute("continent", continentDto);
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
}
