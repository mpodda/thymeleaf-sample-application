package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
//@SessionAttributes({"data", "pagingAndSortingDto"})
public class BaseController {

	@ModelAttribute("currentUrl")
	public String getCurrentUrl(HttpServletRequest request, Model model, HttpSession httpSession) {
	    return request.getRequestURI();
	}
	
//	@ModelAttribute("pagingAndSortingDto")
//	public PagingAndSortingDto getPagingAndSortingDto() {
//		System.out.println("Instaciate pagingAndSortingDto");
//		return new PagingAndSortingDto();
//	}
	
	@ModelAttribute("pagingAndSortingDto")
	public Map<String, PagingAndSortingDto> getPagingAndSortingDto() {
		System.out.println("Instatiate pagingAndSortingDto Map");
		return new HashMap<String, PagingAndSortingDto>();
	}
	
}
