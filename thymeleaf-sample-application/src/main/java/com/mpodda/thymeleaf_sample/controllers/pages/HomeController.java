package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.annotations.SessionalDto;
import com.mpodda.thymeleaf_sample.annotations.SessionalMethod;
import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;
import com.mpodda.thymeleaf_sample.domain.entities.Continent;
import com.mpodda.thymeleaf_sample.utils.RandomDateGenerator;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
	private List<ContinentDto> continentsList;
	
	private List<PersonDto> personsList;

	
	@SessionalDto(sessionAttributeName = "continents")
	public List<ContinentDto> getContinentsList() {
		return continentsList;
	}
	
	@SessionalDto(sessionAttributeName = "persons")
	public List<PersonDto> getPersonsList() {
		return personsList;
	}
	
	@SessionalMethod(sessionAttributeNames = {"continents", "persons"})
//	@SessionalMethod(sessionAttributeNames = {"persons"})
	@GetMapping({"/home2"})
    public String home2(Model model, HttpSession httpSession/*, @ModelAttribute PersonDto personDto*/) {
		this.continentsList = this.loadContinents();
		
		this.personsList = this.loadPersons();
		
		System.out.println("Home 2 before return");
		
		return "application/home2";
	}
	


	private List<ContinentDto> loadContinents() {
		List<Continent> continents = new ArrayList<Continent>();
		continents.add(new Continent().id(Long.valueOf(1)).name("Europe"));
		continents.add(new Continent().id(Long.valueOf(2)).name("Asia"));
		continents.add(new Continent().id(Long.valueOf(3)).name("Africa"));
		continents.add(new Continent().id(Long.valueOf(4)).name("North America"));
		continents.add(new Continent().id(Long.valueOf(5)).name("South America"));
		continents.add(new Continent().id(Long.valueOf(6)).name("Australia"));

		List<ContinentDto> continentDtoList = new ArrayList<ContinentDto>(continents.size());
		continents.forEach(c -> {
			continentDtoList.add(ContinentDto.fromEntity(c));
		});
		
		return continentDtoList;
	}
	
	private List<PersonDto> loadPersons() {
		return RandomDateGenerator.generateRandomPersons(100);
		
		/*
		List<PersonDto> personsList = new ArrayList<PersonDto>();
		
		PersonDto marcello = new PersonDto();
		marcello.setId(Long.valueOf(1));
		marcello.setName("Marcello Podda");
		marcello.setAge(55);
		marcello.setDateOfBirth(Date.from(LocalDate.of(1971, 3, 6).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		PersonDto mary = new PersonDto();
		mary.setId(Long.valueOf(2));
		mary.setName("Mary Berry");
		mary.setAge(53);
		mary.setDateOfBirth(Date.from(LocalDate.of(1972, 11, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		PersonDto john = new PersonDto();
		john.setId(Long.valueOf(3));
		john.setName("John Lord");
		john.setAge(80);
		john.setDateOfBirth(Date.from(LocalDate.of(1946, 02, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		personsList.add(marcello);
		personsList.add(mary);
		personsList.add(john);
		
		return personsList;
		*/
		
	}
}
