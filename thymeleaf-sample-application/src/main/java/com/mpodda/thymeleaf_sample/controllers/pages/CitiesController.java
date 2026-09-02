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
import org.springframework.web.bind.annotation.RequestParam;

import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AddValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminController;
import com.mpodda.thymeleaf_sample.annotations.administration.AdminIdParameter;
import com.mpodda.thymeleaf_sample.annotations.administration.EditValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.ListValueMethod;
import com.mpodda.thymeleaf_sample.annotations.administration.SaveValueMethod;
import com.mpodda.thymeleaf_sample.domain.dto.CityDto;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.service.CityService;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.service.CountryService;
import com.mpodda.thymeleaf_sample.validators.CityDtoValidator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@AdminController(sessionAttribute = "cities", reference = "city")
@Controller
public class CitiesController extends BaseController {
	private ContinentService continentService;
	private CountryService countryService;
	private CityService cityService;
	
	private CityDtoValidator cityDtoValidator;

	public CitiesController(ContinentService continentService, CountryService countryService, CityService cityService, CityDtoValidator cityDtoValidator) {
		this.continentService = continentService;
		this.countryService = countryService;
		this.cityService = cityService;
		this.cityDtoValidator = cityDtoValidator;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {
		binder.addValidators(this.cityDtoValidator);
	}
	
	@SessionalDto
	public List<CityDto> getCitiesList() {
		return this.cityService.allDto();
	}
	
	@SessionalMethod
	@GetMapping({"/cities"})
	public String cities(Model model, HttpSession httpSession) {
		return "application/cities";
	}
	
	@AddValueMethod
	@SelectialMethod(preservedSessionAttributeNames = {"object", "filteredContinents", "filteredCountries"})
	@GetMapping({"/new-city"})
	public String newCity(Model model, HttpSession httpSession, HttpServletResponse response) {
		model.addAttribute("object", this.cityService.dtoDefaultInstance());
		
		final List<ContinentDto> continentsList = continentService.allDto();
		model.addAttribute("filteredContinents", continentsList);
		
		try {
			model.addAttribute("filteredCountries", continentsList.isEmpty() ? new ArrayList<CountryDto>(0) :  this.countryService.filterByContinent(continentsList.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/cities-fragments :: edit-city";
	}
	
	@EditValueMethod
	@SelectialMethod(preservedSessionAttributeNames = {"object", "filteredContinents", "filteredCountries"})
	@GetMapping({"/edit-city"})
	public String editCity(@AdminIdParameter @RequestParam (required = false) Long cityId, Model model, HttpSession httpSession, HttpServletResponse response) {
		try {
			final CityDto cityDto = this.cityService.dtoByEntityId(cityId);
			model.addAttribute("object", cityDto);
			
			model.addAttribute("filteredContinents", continentService.allDto());
			
			if (cityDto.getCountry() != null && cityDto.getCountry().getContinent() != null) {
				model.addAttribute("filteredCountries", this.countryService.filterByContinent(cityDto.getCountry().getContinent()));	
			} else {
				model.addAttribute("filteredCountries", new ArrayList<CountryDto>(0));
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/cities-fragments :: edit-city";
	}
	
	@SaveValueMethod
	@PersisterialMethod(preservedModelAttributeNames= {"filteredContinents", "filteredCountries"})
	@Transactional
	@PostMapping({"/save-city"})
	public String saveCity(@Validated @ModelAttribute CityDto modelAttribute, Errors errors, Model model, HttpSession httpSession, HttpServletResponse response) {
		if (!errors.getFieldErrors().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			model.addAttribute("fieldErrors", errors.getFieldErrors());
		} else {
			try {
				modelAttribute = this.cityService.saveFromDto(modelAttribute);
			} catch (Exception e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				e.printStackTrace();
			}
			response.setStatus(HttpStatus.OK.value());
		}
		
		return "application/fragments/cities-fragments :: edit-city";
	}
	
	@ListValueMethod
	@SessionalMethod(sessionAttributeNames = {"cities"})
	@GetMapping({"/list-cities"})
	public String listCities(Model model, HttpSession httpSession) {
		return "application/fragments/cities-fragments :: cities-list";
	}
}
