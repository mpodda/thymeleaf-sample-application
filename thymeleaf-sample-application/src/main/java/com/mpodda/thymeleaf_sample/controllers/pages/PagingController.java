package com.mpodda.thymeleaf_sample.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PagingController {
	@PostMapping("/paging")
	public String paging(@ModelAttribute PagingAndSortingDto pagingAndSortingDto, Model model, HttpServletResponse response) {
		return null;
	}
}
