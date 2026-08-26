package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.annotations.SelectialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.service.CountryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public abstract class BaseController {
	protected WebDataBinder binder;
	
	@Autowired
	protected MessageSource messageSource;
	
	protected abstract void addValidators(WebDataBinder binder);
	

	@InitBinder
    public void initBinder(WebDataBinder binder) {
    	try {
    		addValidators(binder);
        } catch (Exception e) {
           
        }
    	
    	this.binder = binder;
    }    


	@ModelAttribute("currentUrl")
	public String getCurrentUrl(HttpServletRequest request, Model model, HttpSession httpSession) {
	    return request.getRequestURI();
	}
	
	
	@ModelAttribute("pagingAndSortingDto")
	public Map<String, PagingSortingAndFilteringDto> getPagingAndSortingDtoMap() {
		//System.out.println("Instantiate pagingAndSortingDto Map");
		return new HashMap<String, PagingSortingAndFilteringDto>();
	}
	
	protected String getMessage(final @NonNull String code) {
		return this.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}
}
