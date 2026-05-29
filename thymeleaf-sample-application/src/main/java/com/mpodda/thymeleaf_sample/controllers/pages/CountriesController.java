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
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.service.CountryService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CountriesController extends BaseController {
	private CountryService countryService;
	
	private List<CountryDto> countriesList;

	public CountriesController(CountryService countryService) {
		this.countryService = countryService;
	}

	@Override
	protected void addValidators(WebDataBinder binder) {
		// TODO Auto-generated method stub

	}

	@SessionalDto(sessionAttributeName = "countries")
	public List<CountryDto> getCountriesList() {
		return countriesList;
	}
	
	@SessionalMethod(sessionAttributeNames = {"countries"})
	@GetMapping({"/countries"})
	public String continents(Model model, HttpSession httpSession) {
		this.countriesList = this.countryService.allDto();
		
		return "application/countries";
	}
	
	@GetMapping({"/new-country"})
	public String newContinent(Model model) {
		model.addAttribute("country", this.countryService.dtoDefaultInstance());
		
		return "application/fragments/countries-fragments :: edit-country";
	}
	
	@GetMapping({"/edit-country"})
	public String editContinent(@RequestParam (required = false) Long countryId, Model model, HttpServletResponse response) {
		try {
			model.addAttribute("country", this.countryService.dtoByEntityId(countryId));
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		return "application/fragments/countries-fragments :: edit-country";
	}
	
	@SessionalMethod(sessionAttributeNames = {"countries"})
	@GetMapping({"/list-countries"})
	public String listCountries( Model model, HttpSession httpSession) {
		this.countriesList = this.countryService.allDto();
		
		return "application/fragments/countries-fragments :: countries-list";
	}
	
	@Transactional
	@PostMapping({"/save-country"})
	public String saveCountry(@Validated @ModelAttribute CountryDto countryDto, Errors errors, Model model, HttpServletResponse response) {
		if (!errors.getFieldErrors().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			model.addAttribute("fieldErrors", errors.getFieldErrors());
		} else {
			try {
				countryDto = this.countryService.saveFromDto(countryDto);
			} catch (Exception e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				e.printStackTrace();
			}
			
			response.setStatus(HttpStatus.OK.value());
		}
		
		model.addAttribute("country", countryDto);
		
		return "application/fragments/countries-fragments :: edit-country";
	}	
}
