package com.mpodda.thymeleaf_sample.controllers.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.mpodda.thymeleaf_sample.config.WebConfiguration;
import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;
import com.mpodda.thymeleaf_sample.domain.enums.PagingAndSortingConstants;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;
import com.mpodda.thymeleaf_sample.utils.Serializer;
import com.mpodda.thymeleaf_sample.web.PagingAndSortingService;

import jakarta.servlet.http.HttpSession;

@Controller
//@SessionAttributes({"pagingAndSortingDto", "data"})
public class SortingController<Dto extends BaseDto> {

    private final WebConfiguration webConfiguration;

	@SuppressWarnings("rawtypes")
	@Autowired
    private FindByIndexNameSessionRepository sessionRepository;

	@Autowired
	private PagingAndSortingService<Dto> pagingAndSortingService;
	
	@Value("${tsa.page-size}")
	private int pageSize;

    SortingController(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
    } 
	
	@SuppressWarnings({"unchecked", "null"})
	@PostMapping("/sort")
	public String sort(Model model, @ModelAttribute PagingAndSortingDto pagingAndSortingDto, HttpSession httpSession) {
		System.out.println(String.format("sort :: pagingAndSortingDto: %s", Serializer.objectToJsonString(pagingAndSortingDto)));
		
		Map<String, PagingAndSortingDto> map = new HashMap<String, PagingAndSortingDto>(); 
		
		pagingAndSortingDto = defineSortingDirection(pagingAndSortingDto);
		
		final String fieldName = pagingAndSortingDto.getCurrentSortField();
		
		final String sortDirection = pagingAndSortingDto.getCurrentSortDirection();
		
		Session session = this.sessionRepository.findById(httpSession.getId());
		
		if (session != null && session.getAttribute(SessionalConstants.DATA.value()) != null) {
			Map<String, List<Dto>> map2 = (Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value());
			//System.out.println(String.format("keySet: %s", map2.keySet()));
			
			List<Dto> data = ((Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value())).get(pagingAndSortingDto.getSessionAttribute());
			
			PagedListHolder<Dto> pagedListHolder = new PagedListHolder<Dto>(data);
			
			final SortDefinition sortDefinition = new SortDefinition() {
				@Override
				public boolean isIgnoreCase() {
					return true;
				}
				
				@Override
				public boolean isAscending() {
					return sortDirection.equals(PagingAndSortingConstants.ASC.stringValue());
				}
				
				@Override
				public String getProperty() {
					return fieldName;
				}
			};
			
			pagedListHolder.setSort(sortDefinition);
			pagedListHolder.resort();
			
			pagedListHolder.setPageSize(this.pageSize);
			pagedListHolder.setPage(PagingAndSortingConstants.DEFAULT_PAGE.intValue());
			
			
			pagingAndSortingDto = this.pagingAndSortingService.updatePagingData(pagingAndSortingDto, pagedListHolder);
			
			//System.out.println(String.format("sort :: pagingAndSortingDto: %s", Serializer.objectToJsonString(pagingAndSortingDto)));
			
			model.addAttribute(pagingAndSortingDto.getSessionAttribute(), pagedListHolder.getPageList());
			
			map.put(pagingAndSortingDto.getSessionAttribute(), pagingAndSortingDto);
			
			final String sessionAttribute = pagingAndSortingDto.getSessionAttribute();
			
			map2.keySet().forEach(key-> {
				if (!key.equals(sessionAttribute)) {
					map.put(key, new PagingAndSortingDto());
				}
			});
			
			model.addAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value(), map);
			
		} else {
			//TODO: Send an message to a REST end point. End point will transfer the message to via Web socket in order to "refresh" somehow the page in question to create session.
			//TODO: Investigate later the above idea. ACHTUNG!!! Do not create infinite loop. If after message sending the code lands here again, raise error message to user and 
			//      do not resent message
		}
		
		//TODO: Use constant
		//return new StringBuilder("application").append(pagingAndSortingDto.getViewName()).toString();
		
//		return new StringBuilder("application").append("/home2").toString();
		
		//return "application/fragments/persons-fragments :: persons-list";
		
		return new StringBuilder("application/fragments/").append(pagingAndSortingDto.getSessionAttribute()).append("-fragments :: ").append(pagingAndSortingDto.getSessionAttribute()).append("-list").toString();
		
	}

	
	private static PagingAndSortingDto defineSortingDirection(PagingAndSortingDto sortingDto) {
		if (sortingDto.getCurrentSortDirection() == "") {
			sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DEFALUT_SORTING_DIRECTION.stringValue());
			
			return sortingDto;
		}
		
		if (sortingDto.getCurrentSortDirection().equalsIgnoreCase(PagingAndSortingConstants.ASC.stringValue())) {
			sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DESC.stringValue());
			
			return sortingDto;
		}
		
		sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DEFALUT_SORTING_DIRECTION.stringValue());
		
		return sortingDto;
	}
}
