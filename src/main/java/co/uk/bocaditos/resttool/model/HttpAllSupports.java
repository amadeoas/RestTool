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

	/**
	 * The version with format: M.m.ppp that corresponds to 'M' major, 'm' minor and 'ppp' patch.
	 */
	private final String version; // the version of the data
	/**
	 * Supported APIs information.
	 */
	private final List<HttpSupport> supports;
	/**
	 * JSON representation of the instance of this class.
	 */
	private final String json; // JSON form of the full object 


	public HttpAllSupports(final ObjectMapper mapper) throws IOException {
		logger.info("Loading supported APIs...");

		int i = 0;
		HttpSupport support;

		this.version = "0.0";
		this.supports = new ArrayList<>();
		while ((support = loadJson(mapper, "option_" + ++i + ".json")) != null) {
			this.supports.add(support);
			logger.debug("Loaded API {} definitions at index {}", support.getName(), i - 1);
		}
		this.json = mapper.writeValueAsString(this);
		logger.info("Loaded {} supported APIs v{}", this.supports.size(), this.version);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param supported the supports.
	 */
	public HttpAllSupports(final HttpAllSupports supports) {
		this.version = supports.version;
		this.supports = supports.supports;
		this.json = supports.json;
	}
	
	public final String getVersion() {
		return this.version;
	}
	
	public final List<HttpSupport> getSupports() {
		return this.supports;
	}
	
	public final int getNumApis() {
		return this.supports.size();
	}
	
	public HttpSupport get(final int index) {
		return this.supports.get(index);
	}

	@Override
	public final String toString() {
		return this.json;
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
