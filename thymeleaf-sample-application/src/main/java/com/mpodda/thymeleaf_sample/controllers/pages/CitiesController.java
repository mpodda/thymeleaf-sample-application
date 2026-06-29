package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.annotations.FilterialMethod;
import com.mpodda.thymeleaf_sample.annotations.FilterialPreservations;
import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.CityDto;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.dto.FilterDto;
import com.mpodda.thymeleaf_sample.service.CityService;
import com.mpodda.thymeleaf_sample.service.ContinentService;
import com.mpodda.thymeleaf_sample.service.CountryFilterService;
import com.mpodda.thymeleaf_sample.service.CountryService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CitiesController extends BaseController {
	private ContinentService continentService;
	private CountryService countryService;
	private CityService cityService;
	
	private CountryFilterService countryFilterService;

	
	public CitiesController(ContinentService continentService, CountryService countryService, CityService cityService,
			CountryFilterService countryFilterService) {
		this.continentService = continentService;
		this.countryService = countryService;
		this.cityService = cityService;
		this.countryFilterService = countryFilterService;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {

	}
	
	@SessionalDto(sessionAttributeName = "cities")
	public List<CityDto> getCitiesList() {
		return this.cityService.allDto();
	}
	
	@SessionalMethod(sessionAttributeNames = {"cities"})
	@GetMapping({"/cities"})
	public String cities(Model model, HttpSession httpSession) {
		return "application/cities";
	}
	
	@FilterialPreservations(modelAttributeNames={"city"})
	@GetMapping({"/new-city"})
	public String newCity(Model model, HttpSession httpSession, HttpServletResponse response) {
		model.addAttribute("city", this.cityService.dtoDefaultInstance());
		
		final List<ContinentDto> continentsList = continentService.allDto();
		
		model.addAttribute("filteredContinents", continentsList);
		try {
			model.addAttribute("filteredCountries", continentsList.isEmpty() ? new ArrayList<CountryDto>(0) :  this.countryService.fromEntityList(this.countryFilterService.filterByContinent(continentsList.get(0))));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/cities-fragments :: edit-city";
	}
	
	@SessionalMethod(sessionAttributeNames = {"cities"})
	@GetMapping({"/list-cities"})
	public String listCities(Model model, HttpSession httpSession) {
		return "application/fragments/cities-fragments :: cities-list";
	}
	
	@FilterialMethod(preservedModelAttributeNames={"city"})
	@PostMapping({"/filter-countries"})
	public String filterCountries(Model model, HttpSession httpSession, @ModelAttribute FilterDto<ContinentDto> filterDto, HttpServletResponse response) {
		try {
			model.addAttribute("filteredCountries", this.countryService.fromEntityList(this.countryFilterService.filterByContinent(filterDto.getDto())));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		response.setStatus(HttpStatus.OK.value());
		
		return "application/fragments/cities-fragments :: edit-city";
	}
}
