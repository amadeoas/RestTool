package co.uk.bocaditos.transtrack.config;

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

} // end class FreeMarkerViewResolver
