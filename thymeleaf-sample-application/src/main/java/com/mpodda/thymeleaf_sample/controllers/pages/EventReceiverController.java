package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.domain.dto.CityDto;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;
import com.mpodda.thymeleaf_sample.service.CityService;
import com.mpodda.thymeleaf_sample.utils.Serializer;

import jakarta.servlet.http.HttpSession;

@Controller
public class EventReceiverController {
	private CityService cityService;
	
	public EventReceiverController(CityService cityService) {
		this.cityService = cityService;
	}

	@PostMapping("/on-value-change")
	public String onValueChange(Model model, @ModelAttribute OnValueChangeDto onValueChangeDto, HttpSession httpSession) {
		System.out.println(String.format("EventReceiverController:: onValueChange:: onValueChangeDto=%s", Serializer.objectToJsonString(onValueChangeDto)));
		
		System.out.println(String.format("object from session: %s", httpSession.getAttribute("object")));
		
//		model.addAttribute("city", this.cityService.dtoDefaultInstance());
		model.addAttribute("object", this.cityService.dtoDefaultInstance());
		
		model.addAttribute("filteredContinents", new ArrayList<ContinentDto>());
		model.addAttribute("filteredCountries", /*new ArrayList<CountryDto>()*/null);
		
		model.addAttribute("onValueChangeDto", onValueChangeDto);
		
		
		return onValueChangeDto.getFragmentUrl();
	}
}
