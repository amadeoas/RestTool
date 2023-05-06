package co.uk.bocaditos.resttool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;


/**
 * .
 * 
 * @author aasco
 */
@Configuration
public class FreeMarkerViewConfig {

	@Bean 
	public FreeMarkerViewResolver freemarkerViewResolver() { 
	    final FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 

	    resolver.setCache(true); 
	    resolver.setPrefix(""); 
	    resolver.setSuffix(".ftl"); 

	    return resolver; 
	}
//
//	@Bean 
//	public FreeMarkerConfigurer freemarkerConfig() { 
//	    final FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//
//	    freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
//
//	    return freeMarkerConfigurer; 
//	}

} // end class FreeMarkerViewResolver
