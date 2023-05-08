package co.uk.bocaditos.resttool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
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

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

} // end class FreeMarkerViewResolver
