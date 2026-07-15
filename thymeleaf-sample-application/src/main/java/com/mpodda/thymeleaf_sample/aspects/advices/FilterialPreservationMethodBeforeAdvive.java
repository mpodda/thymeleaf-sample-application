package com.mpodda.thymeleaf_sample.aspects.advices;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.mpodda.thymeleaf_sample.annotations.FilterialMethod;
import com.mpodda.thymeleaf_sample.domain.dto.BaseIdentifiableDto;
import com.mpodda.thymeleaf_sample.domain.dto.FilterDto;

import jakarta.servlet.http.HttpSession;

@Component
public class FilterialPreservationMethodBeforeAdvive<Dto extends BaseIdentifiableDto<?, ?>> implements MethodBeforeAdvice {

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public void before(@NonNull Method method, Object[] args, @Nullable Object target) throws Throwable {
		if (AdviceUtils.isFilterialPreservation(method)) {
			Model model = AdviceUtils.locateModel(args); 
			HttpSession httpSession = AdviceUtils.locateHttpSession(args);
			
			if (model == null || httpSession == null) {
				return;
			}
			
			final FilterDto<Dto> filterDto = (FilterDto<Dto>)model.getAttribute("filterDto");
			
			if (filterDto != null) {
				model.addAttribute("randomSuffix", filterDto.getRandomSuffix());
			}
			
			final String[] modelAttributeNames = method.getAnnotation(FilterialMethod.class).preservedModelAttributeNames();
			
			Arrays.stream(modelAttributeNames).forEach(m -> {
				model.addAttribute(m, httpSession.getAttribute(m));
			});
		}
	}
}
