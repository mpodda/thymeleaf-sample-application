package com.mpodda.thymeleaf_sample.thymeleaf;

import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IEngineContext;
import org.thymeleaf.engine.TemplateData;

public final class FragmentUtils {

	 public static String fragmentUrl(IContext context) {
		 IEngineContext engineContext = (IEngineContext)context;
		 
		 final TemplateData fragmentTemplateData = engineContext.getTemplateStack().get(engineContext.getTemplateStack().size() - 2);
		 
		 StringBuilder fragmentPathStringBuilder = new StringBuilder(fragmentTemplateData.getTemplate()).append(" :: ").append(fragmentTemplateData.getTemplateSelectors().iterator().next());
		 
		 return fragmentPathStringBuilder.toString();
	 }
	 
	 public static String onValueChangeFormComponentfragmentUrl(IContext context) {
		 IEngineContext engineContext = (IEngineContext)context;
		 
		 final TemplateData fragmentTemplateData = engineContext.getTemplateStack().get(0);
		 
		 return new StringBuilder(fragmentTemplateData.getTemplate()).append(" :: ").append(fragmentTemplateData.getTemplateSelectors().iterator().next()).toString();
	 }
	 
	 public static void javascript(IContext context) {
		 System.out.println("javascript");
	 }
}
 