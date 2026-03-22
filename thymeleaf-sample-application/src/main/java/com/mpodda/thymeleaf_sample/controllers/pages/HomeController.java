package com.mpodda.thymeleaf_sample.controllers.pages;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
	@GetMapping({"/home2"})
    public String home2(Model model, HttpSession httpSession, @ModelAttribute PersonDto personDto) {
		/*
		@SessionalDto(sessionAttributeName = "continents")
		final List<ContinentDto> continentsList = this.loadContinents();
		model.addAttribute("continents", continentsList);
		addToSession("continents", continentsList);
		
		@SessionalDto(sessionAttributeName = "persons")
		final List<PersonDto> personsList = this.loadPersons();
		model.addAttribute("persons", personsList);
		addToSession("persons", personsList);
		*/
		
		this.continentsList = this.loadContinents();
		//model.addAttribute("continents", continentsList);
		//addToSession("continents", continentsList);

		
		this.personsList = this.loadPersons();
		//model.addAttribute("persons", personsList);
		//addToSession("persons", personsList);
		
		
		return "application/home2";
	}
	
	
//	@GetMapping({"/"})
//    public String home(Model model, HttpSession httpSession) {
//		
//////		System.out.println(String.format("Session id: %s", httpSession.getId()));
////		
////		Session session = this.sessionRepository.findById(httpSession.getId());
////		
////		
////		System.out.println(String.format("home :: Session found: %s", session != null));
////		
////		if (session != null) {
////			this.sessionRepository.deleteById(httpSession.getId());
////			
////			System.out.println("home :: Clear Session");
////			model.addAttribute("continents", data());
////		} else {
////			model.addAttribute("continents", model.getAttribute("data"));
////		}
////		
////		//session = this.sessionRepository.findById(httpSession.getId());
////		
//////		System.out.println(String.format("Session found [2]: %s", session != null));
//
//		
//		// ========================================================================================
//		
//		Session session = this.sessionRepository.findById(httpSession.getId());
//		
//		if (session == null) {
//			session = this.sessionRepository.createSession();
//		}
//		
//		model.addAttribute("continents", this.loadContinents());
//		
//		//model.addAttribute("sortingDto", this.getSortingDto());
//		
//		session.setAttribute("data", this.loadContinents());
//		
//		this.sessionRepository.save(session);
//		
//		return "application/home";
//    }
	
	
//	@GetMapping({"/model"})
//    public String model(Model model) {
//		
//		System.out.println("===================================================");
//		
//		System.out.println("Model!");
//		//System.out.println(String.format("continents: %s", model.getAttribute("{continents")));
//		System.out.println(String.format("model as map: %s", model.asMap()));
//		
//		List<Continent> continents = (List<Continent>)model.getAttribute("data");
//		
//		continents.remove(0);
//		
//		System.out.println(String.format("continents now=%s", continents));
//		
//		model.addAttribute("data", continents);
//		
//		return "application/home";
//	}

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
		return RandomDateGenerator.generateRandomPersons(15);
		
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
	

//	@Override
//	protected Object data() {
//		System.out.println("---------------");
//		System.out.println("data()");
//		System.out.println("---------------");
//
//		
//		List<Continent> continents = new ArrayList<Continent>();
//		continents.add(new Continent().id(Long.valueOf(1)).name("Europe"));
//		continents.add(new Continent().id(Long.valueOf(2)).name("Asia"));
//		continents.add(new Continent().id(Long.valueOf(3)).name("Africa"));
//		continents.add(new Continent().id(Long.valueOf(4)).name("North America"));
//		continents.add(new Continent().id(Long.valueOf(5)).name("South America"));
//		continents.add(new Continent().id(Long.valueOf(6)).name("Australia"));
//		
////		continents.sort(new Comparator<Continent>() {
////
////			@Override
////			public int compare(Continent o1, Continent o2) {
////				try {
////					Field field1 = o1.getClass().getDeclaredField("name");
////					field1.setAccessible(true);
////					
////					Field field2 = o2.getClass().getDeclaredField("name");
////					field2.setAccessible(true);
////					
////					final Object valueO1 = field1.get(o1);
////					final Object valueO2 = field2.get(o2);
////					
////					return ((String)valueO1).compareToIgnoreCase(((String)valueO2));
////				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
////						| SecurityException e) {
////					e.printStackTrace();
////				}
////				
////				return 0;
////			}
////		});
//		
//		List<ContinentDto> continentDtoList = new ArrayList<ContinentDto>(continents.size());
//		continents.forEach(c -> {
//			continentDtoList.add(ContinentDto.fromEntity(c));
//		});
//		
//		return continentDtoList;
//	}
	
	
}
