package com.mpodda.thymeleaf_sample.thymeleaf;

import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IEngineContext;
import org.thymeleaf.engine.TemplateData;

import com.mpodda.thymeleaf_sample.utils.Serializer;

public final class FragmentUtils {

	 public static String fragmentUrl(IContext context) {
		 
		 IEngineContext engineContext = (IEngineContext)context;
		 
//		 TemplateManager templateManager = engineContext.getConfiguration().getTemplateManager();

//		 TemplateData templateData = we.getTemplateData();
		 
//		 System.out.println(String.format("TemplateSelectors: %s", templateData.getTemplateSelectors()));
		 
//		 System.out.println(String.format("TemplateResource: %s", templateData.getTemplateResource().getBaseName()));
		 
//		 System.out.println(String.format("TemplateResolutionAttributes: %s", we.getTemplateResolutionAttributes()));
		 
		 //System.out.println(String.format("TemplateStack: %s", Serializer.objectToJsonString(we.getTemplateStack())));
		 
		 /*
		 System.out.println("TemplateStack:");
		 we.getTemplateStack().forEach(templateStack -> {
			 System.out.println(String.format("%s", Serializer.objectToJsonString(templateStack)));
		 });
		 */
		 
		 final TemplateData fragmentTemplateData = engineContext.getTemplateStack().get(engineContext.getTemplateStack().size() - 2);
		 
//		 System.out.println(String.format("fragmentTemplateData: %s", Serializer.objectToJsonString(fragmentTemplateData)));
		 
		 StringBuilder fragmentPathStringBuilder = new StringBuilder(fragmentTemplateData.getTemplate()).append(" :: ").append(fragmentTemplateData.getTemplateSelectors().iterator().next());
		 
		 
		 return fragmentPathStringBuilder.toString();
	 }
}
