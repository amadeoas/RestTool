package co.uk.bocaditos.resttool.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.bocaditos.resttool.model.HttpAllSupportsResponses;
import co.uk.bocaditos.resttool.model.ViewModelException;


/**
 * .
 * 
 * @author aasco
 */
@Configuration
public class ModelConfiguration {

	@Bean
	public HttpAllSupportsResponses support(final ObjectMapper mapper) 
			throws IOException, ViewModelException {
		return new HttpAllSupportsResponses(mapper);
	}

} // end class ModelConfiguration
