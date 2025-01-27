package co.uk.bocaditos.transtrack.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.bocaditos.transtrack.model.ResponseModel;
import co.uk.bocaditos.transtrack.model.ViewModelException;


/**
 * .
 * 
 * @author aasco
 */
@Configuration
public class ModelConfiguration {

	@Bean
	public ResponseModel support(@Value("${ui.name}") final String name, 
			@Value("${ui.version}") final String version, @Autowired final ObjectMapper mapper, 
			@Autowired final Environment env ) 
			throws IOException, ViewModelException {
		return new ResponseModel(name, version, mapper, env);
	}

} // end class ModelConfiguration
