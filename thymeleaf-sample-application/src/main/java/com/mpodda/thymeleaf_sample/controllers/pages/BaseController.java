package com.mpodda.thymeleaf_sample.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mpodda.thymeleaf_sample.domain.dto.PagingAndSortingDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
//@SessionAttributes({"data", "pagingAndSortingDto"})
public class BaseController {

	@ModelAttribute("currentUrl")
	public String getCurrentUrl(HttpServletRequest request, Model model, HttpSession httpSession) {
	    return request.getRequestURI();
	}
	
	@ModelAttribute("pagingAndSortingDto")
	public PagingAndSortingDto getPagingAndSortingDto() {
		return new PagingAndSortingDto();
	}
	
	/*
	@ModelAttribute("sortingDto")
	public SortingDto getSortingDto() {
		return new SortingDto();
	}
	*/
	
//	@Autowired
//    protected FindByIndexNameSessionRepository sessionRepository;
	
	//private Map<String, List<? extends BaseIdentifiableDto>> map = null; //new HashMap<String, List<? extends BaseIdentifiableDto>>();
	

	//@ModelAttribute("data")
//	protected abstract Object data();
//	private String sessionId;

  
	
//	@PostConstruct
//	private void init() {
//		System.out.println("Init BaseController:");
//		
//		if (this.map == null) {
//			this.map = new HashMap<String, List<? extends BaseIdentifiableDto>>();
//			System.out.println("mpa is null");
//		} else {
//			System.out.println("mpa is NOT null");
//		}
//	}
	
//	protected Session getSession() {
//		Session session = this.sessionRepository.findById(RequestContextHolder.currentRequestAttributes().getSessionId());
//		
//		if (session == null) {
//			return this.sessionRepository.createSession();
//		}
//		
//		return session;
//	}
	
//	protected void addToSession(final String sessionAttribute, List<? extends BaseIdentifiableDto> data) {
//		map.put(sessionAttribute, data);
//		
//		Session session = this.getSession();
//		session.setAttribute("data", map);
//		this.sessionRepository.save(session);
//	}
	
	/*
	protected void saveSessionAttribute(Object value) {
		this.getSession().setAttribute("data", value);
		this.sessionRepository.save(getSession());
	}
	*/
	

	
	 
	
	/*
	public static void main(String[] args) {
		System.out.println("Hello!");
		
		final Integer random = Integer.valueOf((int)(Math.random() * 10000));
		
		System.out.println(random);
		
	}
	*/	

	//@ModelAttribute("pagingAndSortingDto")
//	public PagingAndSortingDto getPagingAndSortingDto(HttpServletRequest request, HttpSession httpSession) {
//		// System.out.println(String.format("getPagingAndSortingDto :: Session id: %s", httpSession.getId()));
//		
//		Session session = this.sessionRepository.findById(httpSession.getId());
//		
//		if (session != null) {
//			if (session.getAttribute("pagingAndSortingDto") != null) {
//				return (PagingAndSortingDto)session.getAttribute("pagingAndSortingDto");
//			}
//		}
//		
//		PagingAndSortingDto pagingAndSortingDto = new PagingAndSortingDto();
//		
//		pagingAndSortingDto.setViewName(request.getRequestURI());
//		pagingAndSortingDto.setPageOffset(Integer.valueOf(3));
//		
////		System.out.println(String.format("pagingAndSortingDto=%s", pagingAndSortingDto));
//		
//		return pagingAndSortingDto;
//	}
	

	
}
