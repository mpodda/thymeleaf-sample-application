package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.CityDto;
import com.mpodda.thymeleaf_sample.service.CityService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitiesController extends BaseController {
	private CityService cityService;
	
	public CitiesController(CityService cityService) {
		this.cityService = cityService;
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
}
