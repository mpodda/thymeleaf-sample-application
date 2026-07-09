package com.mpodda.thymeleaf_sample.thymeleaf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.context.IContext;

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.events.OnValueChangeDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

public final class SelectionFilterUtils {
	public static <Dto extends BaseDto> List<Dto> onValueChange(IContext context) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		//System.out.println(String.format("SelectionFilterUtils :: onValueChange : event=%s", event));
		System.out.println(String.format("SelectionFilterUtils :: onValueChange : dto=%s", Serializer.objectToJsonString(context.getVariable("onValueChangeDto"))));
		
		final OnValueChangeDto onValueChangeDto = (OnValueChangeDto)context.getVariable("onValueChangeDto");
		
		if (onValueChangeDto != null) {
			final String eventName = onValueChangeDto.getEvent();
			
			final String beanName = eventName.split("\\.")[0];
			final String methodName = eventName.split("\\.")[1];
			
			System.out.println(String.format("beanName=%s, method=%s", beanName, methodName));
	
			
			final Object bean = SpringContextForThymeleaf.getBeanByName(beanName);
			
			Method method = bean.getClass().getMethod(methodName, String.class);
			
			List<Dto> result = (List<Dto>)method.invoke(bean, onValueChangeDto.getValue());
			
			return result;
		}
		
		return new ArrayList<Dto>();
	}
}
