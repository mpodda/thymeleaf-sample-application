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

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AddValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminController;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminIdParameter;
import com.mpodda.thymeleaf_sample.annotations.administration.EditValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.ListValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.SaveValueMethod;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.validators.ContinentDtoValidator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@AdminController(sessionAttribute = "continents", reference = "continent")
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
	
	@SessionalDto
	public List<ContinentDto> getContinentsList() {
		return continentsList;
	}
	
	@SessionalMethod
	@GetMapping({"/continents"})
	public String continents(Model model, HttpSession httpSession) {
		this.continentsList = this.continentService.allDto();
		
		return "application/continents";
	}
	
	@AddValueMethod
	@GetMapping({"/new-continent"})
	public String newContinent(Model model) {
		model.addAttribute("object", this.continentService.dtoDefaultInstance());
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
	@EditValueMethod
	@GetMapping({"/edit-continent"})
	public String editContinent(@AdminIdParameter @RequestParam (required = false) Long continentId, Model model, HttpServletResponse response) {
		try {
			model.addAttribute("object", this.continentService.dtoByEntityId(continentId));
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
	
	@ListValueMethod
	@SessionalMethod(sessionAttributeNames = {"continents"})
	@GetMapping({"/list-continents"})
	public String listContinents( Model model, HttpSession httpSession) {
		this.continentsList = this.continentService.allDto();
		
		return "application/fragments/continents-fragments :: continents-list";
	}
	
	@SaveValueMethod
	@PersisterialMethod
	@Transactional
	@PostMapping({"/save-continent"})
	public String saveContinent(@Validated @ModelAttribute ContinentDto continentDto, Errors errors, Model model, HttpSession httpSession, HttpServletResponse response) {
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
		
		return "application/fragments/continents-fragments :: edit-continent";
	}
}
