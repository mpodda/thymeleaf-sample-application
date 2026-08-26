package com.mpodda.thymeleaf_sample.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.NotReadablePropertyException;

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

public class FilteredListHolder<Dto extends BaseDto> {
	private final List<Dto> sourceData;

	private Predicate<Dto> filterPredicate = dto -> true;
	
	private final Map<String, PropertyDescriptor> beanReflectionCache = new ConcurrentHashMap<String, PropertyDescriptor>();
	
	public FilteredListHolder(List<Dto> sourceData) {
		this.sourceData = sourceData;
	}

	public FilteredListHolder<Dto> eq(final String field, final String value) {
		Predicate<Dto> predicate = new Predicate<Dto>() {
			
			@Override
			public boolean test(Dto dto) {
				final PropertyDescriptor propertyDescriptor = beanReflectionCache.computeIfAbsent(field, f -> BeanUtils.getPropertyDescriptor(dto.getClass(), f));
				
				try {
					final Object objectValue = propertyDescriptor.getReadMethod().invoke(dto);
					
					return value.compareTo(objectValue.toString()) == 0;
					
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				
				return false;
			}
		};
		
		this.addPredicate(predicate);
		
		return this;
	}
	
	public FilteredListHolder<Dto> contains(final String field, final String value) {
		Predicate<Dto> predicate = new Predicate<Dto>() {

			@Override
			public boolean test(Dto dto) {
				final PropertyDescriptor propertyDescriptor = beanReflectionCache.computeIfAbsent(field, f -> BeanUtils.getPropertyDescriptor(dto.getClass(), f));
				
				if (propertyDescriptor.getPropertyType() == String.class) {
					try {
						final String objectValue = (String)propertyDescriptor.getReadMethod().invoke(dto);
						
						return objectValue != null && value != null && objectValue.toUpperCase().contains(value.toUpperCase());
					} catch(NotReadablePropertyException e) {
						throw new IllegalArgumentException("Unknown field: " + field, e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				
				return false;
			}
		};
		
		this.addPredicate(predicate);
		
		return this;
	}
	
	
	public List<Dto> getFilteredData() {
		return new ArrayList<Dto>(this.sourceData.stream().filter(this.filterPredicate).toList());
		
		//return this.sourceData.stream().filter(this.filterPredicate).toList();
	}
	
	private void addPredicate(final Predicate<Dto> predicate) {
		if (this.filterPredicate == null) {
			this.filterPredicate = predicate;
		} else {
			this.filterPredicate = predicate.and(this.filterPredicate);
		}
	}
}
