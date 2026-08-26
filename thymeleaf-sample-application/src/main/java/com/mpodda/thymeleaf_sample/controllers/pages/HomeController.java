package com.mpodda.thymeleaf_sample.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
	@Override
	protected void addValidators(WebDataBinder binder) {
		
	}
	
	@GetMapping({"/", "/home"})
    public String home(Model model, HttpSession httpSession) {
		return "application/home";
	}
}
