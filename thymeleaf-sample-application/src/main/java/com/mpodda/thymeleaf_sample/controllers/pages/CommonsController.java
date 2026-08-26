package com.mpodda.thymeleaf_sample.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;
import com.mpodda.thymeleaf_sample.service.CountryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommonsController {
	private CountryService countryService;
	
	public CommonsController(CountryService countryService) {
		this.countryService = countryService;
	}

	@SelectialMethod(preservedModelAttributeNames = {"object", "filteredContinents"})
	@PostMapping("/on-continet-value-change")
	public String onContinentValueChange(Model model, HttpSession httpSession, @ModelAttribute OnValueChangeDto onValueChangeDto) throws Exception {
		model.addAttribute("filteredCountries", this.countryService.filterByContinent(onValueChangeDto.getValue()));
		
		return onValueChangeDto.getFragmentUrl();
	}	
}
