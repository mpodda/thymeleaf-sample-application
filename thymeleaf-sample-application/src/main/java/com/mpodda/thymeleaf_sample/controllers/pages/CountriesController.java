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

import com.mpodda.thymeleaf_sample.annotations.FilterialMethod;
import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;
import com.mpodda.thymeleaf_sample.annotations.PersisterialMethod;
import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.dto.FilterDto;
import com.mpodda.thymeleaf_sample.service.ContinentFilterService;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.service.CountryService;
import com.mpodda.thymeleaf_sample.validators.CountryDtoValidator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CountriesController extends BaseController {
	private CountryService countryService;
	
	private ContinentService continentService;
	
	private ContinentFilterService continentFilterService;
	
	private CountryDtoValidator countryDtoValidator;
	
//	private List<CountryDto> countriesList;

	public CountriesController(CountryService countryService, ContinentService continentService,
			ContinentFilterService continentFilterService, CountryDtoValidator countryDtoValidator) {
		this.countryService = countryService;
		this.continentService = continentService;
		this.continentFilterService = continentFilterService;
		this.countryDtoValidator = countryDtoValidator;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {
		binder.addValidators(this.countryDtoValidator);
	}

	@SessionalDto(sessionAttributeName = "countries")
	public List<CountryDto> getCountriesList() {
		return this.countryService.allDto();
//		return countriesList;
	}
	
	@SessionalMethod(sessionAttributeNames = {"countries"})
	@GetMapping({"/countries"})
	public String continents(Model model, HttpSession httpSession) {
//		this.countriesList = this.countryService.allDto();
		
		return "application/countries";
	}
	
	@FilterialPreservations(modelAttributeNames={"country"})
	@GetMapping({"/new-country"})
	public String newContinent(Model model, HttpSession httpSession) {
		model.addAttribute("country", this.countryService.dtoDefaultInstance());
		
		model.addAttribute("filteredContinents", this.continentService.allDto());
		
		return "application/fragments/countries-fragments :: edit-country";
	}
	
	@FilterialPreservations(modelAttributeNames={"country"})
	@GetMapping({"/edit-country"})
	public String editContinent(@RequestParam (required = false) Long countryId, Model model, HttpSession httpSession, HttpServletResponse response) {
		try {
			model.addAttribute("country", this.countryService.dtoByEntityId(countryId));
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/countries-fragments :: edit-country";
	}
	
	@SessionalMethod(sessionAttributeNames = {"countries"})
	@GetMapping({"/list-countries"})
	public String listCountries(Model model, HttpSession httpSession) {
		//this.countriesList = this.countryService.allDto();
		
		return "application/fragments/countries-fragments :: countries-list";
	}
	
	@PersisterialMethod(preservedModelAttributeName = "country")
	@Transactional
	@PostMapping({"/save-country"})
	public String saveCountry(@Validated @ModelAttribute CountryDto modelAttribute, Errors errors, Model model, HttpServletResponse response) {
		if (!errors.getFieldErrors().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			model.addAttribute("fieldErrors", errors.getFieldErrors());
		} else {
			try {
				modelAttribute = this.countryService.saveFromDto(modelAttribute);
				this.continentFilterService.refresh();
			} catch (Exception e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				e.printStackTrace();
			}
			
			response.setStatus(HttpStatus.OK.value());
		}
		
		model.addAttribute("filteredContinents", this.continentService.allDto());
		
		return "application/fragments/countries-fragments :: edit-country";
	}
	
	@FilterialMethod(preservedModelAttributeNames={"country"})
	@PostMapping({"/filter-continents"})
	public String filterContinents(Model model, HttpSession httpSession,  @ModelAttribute FilterDto filterDto, HttpServletResponse response) {
		model.addAttribute("filteredContinents", this.continentService.fromEntityList(this.continentFilterService.filterByName(filterDto.getValue())));
		
		return "application/fragments/countries-fragments :: edit-country";
	}
}
