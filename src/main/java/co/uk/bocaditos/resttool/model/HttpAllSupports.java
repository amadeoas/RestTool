package co.uk.bocaditos.resttool.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Data to access each of the supported APIs.
 * 
 * @author aasco
 */
public class HttpAllSupports {

	private static final Logger logger = LogManager.getLogger(HttpAllSupports.class);

	private final String version; // the version of the data
	private final List<HttpSupport> supports;
	private final List<String> apiNames;


	public HttpAllSupports(final ObjectMapper mapper) throws IOException {
		logger.info("Loading supported APIs...");

		int i = 0;
		HttpSupport support;

		this.version = "0.0";
		this.supports = new ArrayList<>();
		this.apiNames = new ArrayList<>();
		while ((support = loadJson(mapper, "option_" + ++i + ".json")) != null) {
			this.supports.add(support);
			this.apiNames.add(support.getName());
			logger.debug("Loaded API {} definitions at index {}", support.getName(), i - 1);
		}
		logger.info("Loaded {} supported APIs", this.supports.size());
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public List<HttpSupport> getSupports() {
		return this.supports;
	}
    
    public List<String> getApiNames() {
    	return this.apiNames;
    }
	
	public HttpSupport get(final int index) {
		return this.supports.get(index);
	}
    
    public String getApiName(final int index) {
    	return this.apiNames.get(index);
    }
    
    static HttpSupport loadJson(final ObjectMapper mapper, final String filename) throws IOException {
    	final ClassPathResource rsc = new ClassPathResource(filename);

    	try {
	    	final File file = rsc.getFile();
	
	    	return mapper.readValue(file, HttpSupport.class);
    	} catch (final FileNotFoundException ffe) {
    		return null;
    	}
    }

} // end class HttpAllSupports
