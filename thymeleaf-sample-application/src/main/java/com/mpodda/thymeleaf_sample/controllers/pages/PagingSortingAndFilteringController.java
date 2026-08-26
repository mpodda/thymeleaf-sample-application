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

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.domain.enums.PagingAndSortingConstants;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;
import com.mpodda.thymeleaf_sample.utils.FilteredListHolder;
import com.mpodda.thymeleaf_sample.utils.Serializer;
import com.mpodda.thymeleaf_sample.web.PagingAndSortingService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PagingSortingAndFilteringController<Dto extends BaseDto> {

	@SuppressWarnings("rawtypes")
	@Autowired
    private FindByIndexNameSessionRepository sessionRepository;
	
	@Autowired
	private PagingAndSortingService<Dto> pagingAndSortingService;
	
	@Value("${thysa.page-size}")
	private int pageSize;

	@SuppressWarnings({"unchecked", "null"})
	@PostMapping("/paging-sort")
	public String sort(Model model, @ModelAttribute PagingSortingAndFilteringDto pagingAndSortingDto, HttpSession httpSession, HttpServletResponse response) {
		//System.out.println(String.format("pagingAndSortingDto: %s", Serializer.objectToJsonString(pagingAndSortingDto)));
		
		Map<String, PagingSortingAndFilteringDto> modelMap = new HashMap<String, PagingSortingAndFilteringDto>(); 
		
		pagingAndSortingDto = defineSortingDirection(pagingAndSortingDto);
		
		final String fieldName = pagingAndSortingDto.getCurrentSortField();
		
		final String sortDirection = pagingAndSortingDto.getCurrentSortDirection();
		
		Session session = this.sessionRepository.findById(httpSession.getId());
		
		if (session != null && session.getAttribute(SessionalConstants.DATA.value()) != null) {
			Map<String, List<Dto>> sessionMap = (Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value());
			List<Dto> data = ((Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value())).get(pagingAndSortingDto.getSessionAttribute());
			
			/* Filtering */
			if (pagingAndSortingDto.hasFilter()) {
				//data = new FilteredListHolder<Dto>(data).contains(pagingAndSortingDto.getFilterField(), pagingAndSortingDto.getFilterValue()).getFilteredData();
				FilteredListHolder<Dto> filteredListHolder = new FilteredListHolder<Dto>(data);
				
				pagingAndSortingDto.getFilters().forEach(filter -> {
					if (filter.getFilterValue() != null && !filter.getFilterValue().isBlank()) {
						switch (filter.getOperator()) {
							case CONTAINS: 
								filteredListHolder.contains(filter.getFilterField(), filter.getFilterValue());
							break;
							
							case EXACT:
								System.out.println(String.format("EXACT: %s=%s ?", filter.getFilterField(), filter.getFilterValue()));
								filteredListHolder.eq(filter.getFilterField(), filter.getFilterValue());
							break;
							
							case STARTS: break;
						}
					}
				});
				
				data = filteredListHolder.getFilteredData();
				
				//System.out.println(String.format("data=%s", data));
			}
			
			/* Paging */
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
			pagedListHolder.setPage(pagingAndSortingDto.getPageNumber() == null ? PagingAndSortingConstants.DEFAULT_PAGE.intValue() : pagingAndSortingDto.getPageNumber() - 1);
			
			pagingAndSortingDto = this.pagingAndSortingService.updatePagingData(pagingAndSortingDto, pagedListHolder);
			
			model.addAttribute(pagingAndSortingDto.getSessionAttribute(), pagedListHolder.getPageList());
			
			modelMap.put(pagingAndSortingDto.getSessionAttribute(), pagingAndSortingDto);
			
			final String sessionAttribute = pagingAndSortingDto.getSessionAttribute();
			
			sessionMap.keySet().forEach(key-> {
				if (!key.equals(sessionAttribute)) {
					modelMap.put(key, new PagingSortingAndFilteringDto());
				}
			});
			
			model.addAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value(), modelMap);
		} else {
			//TODO: Send an message to a REST end point. End point will transfer the message to via Web socket in order to "refresh" somehow the page in question to create session.
			//TODO: Investigate later the above idea. ACHTUNG!!! Do not create infinite loop. If after message sending the code lands here again, raise error message to user and 
			//      do not resent message
		}
		
		
		//TODO: Remove later
//		FilterDto filterDto = new FilterDto();
//		model.addAttribute("filterDto", filterDto);
		
		
		return pagingAndSortingDto.getFragmentUrl();
	}
	
	private static PagingSortingAndFilteringDto defineSortingDirection(PagingSortingAndFilteringDto sortingDto) {
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

//	@PostMapping("/filter")
//	public String filter(Model model, @ModelAttribute PagingSortingAndFilteringDto pagingSortingAndFilterDto /*FilterDto filterDto*/, HttpSession httpSession) {
//		System.out.println(String.format("filter :: pagingAndSortingDto: %s", Serializer.objectToJsonString(pagingSortingAndFilterDto)));
//		
////		System.out.println(String.format("filter :: filterDto:%s", filterDto));
//		
//		
////		Session session = this.sessionRepository.findById(httpSession.getId());
////		if (session != null && session.getAttribute(SessionalConstants.DATA.value()) != null) {
////			final Map<String, List<Dto>> sessionMap = (Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value());
////			
////			Map<String, PagingSortingAndFilteringDto> modelMap = new HashMap<String, PagingSortingAndFilteringDto>();
////			
////			model.addAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value(), modelMap);
////			
////			//System.out.println(String.format("sessionMap is null? %s", sessionMap == null));
////			
//////			if (sessionMap != null) {
//////				System.out.println("Keys:");
//////				sessionMap.keySet().forEach(k->{
//////					System.out.println(k);
//////				});
//////			}
////			
////			model.addAttribute(filterDto.getSessionAttribute(), sessionMap.get(filterDto.getSessionAttribute()));
////		}
////		
////		return filterDto.getFragmentUrl();
//		
//		Map<String, PagingSortingAndFilteringDto> modelMap = new HashMap<String, PagingSortingAndFilteringDto>();
//		modelMap.put(pagingSortingAndFilterDto.getSessionAttribute(), pagingSortingAndFilterDto);
//		model.addAttribute(SessionalConstants.PAGING_AND_SORTING_MODEL_ATTRIBUTE.value(), modelMap);
//		
//		return pagingSortingAndFilterDto.getFragmentUrl();
//	}
	
}
