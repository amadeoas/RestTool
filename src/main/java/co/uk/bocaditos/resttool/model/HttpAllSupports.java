package co.uk.bocaditos.resttool.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.bocaditos.resttool.controller.RestToolError;


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
	 * The name of this tool.
	 */
	private final String name;
	/**
	 * Supported APIs information.
	 */
	private final List<HttpSupport> supports;
	/**
	 * JSON representation of the instance of this class.
	 */
	private final String json; // JSON form of the full object 


	public HttpAllSupports(final String name, final ObjectMapper mapper) throws IOException {
		logger.info("Loading supported APIs...");

		int i = 0;
		HttpSupport support;

		this.version = "0.0";
		this.name = name;
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
		this.name = supports.name;
		this.supports = supports.supports;
		this.json = supports.json;
	}
	
	public final String getVersion() {
		return this.version;
	}

	public final String getName() {
		return this.name;
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

	public HttpMethod getMethod(final ApiRequest request) {
		final HttpSupport support = this.supports.get(request.getApiIndex());
		final HttpFunction func = support.getFuncs().get(request.getFuncIndex());

		return func.getMethod();
    }

	public String getRestHost(final ApiRequest request) throws RestToolError {
		final StringBuilder buf = new StringBuilder();
		final HttpSupport support = this.supports.get(request.getApiIndex());
		final HttpFunction func = support.getFuncs().get(request.getFuncIndex());
		final HttpEnvironment env = support.getEnvs().get(request.getEnvIndex());

		buf.append(env.getRoute());
		if (exists(func.getQuery())) {
			buf.append('/')
				.append(func.getQuery());
		}

		return getRestQueryParams(func.getParams(), request, buf).toString();
	}

	public StringBuilder getRestQueryParams(final List<HttpField> paramDefs, final ApiRequest request, 
			final StringBuilder buf) throws RestToolError {
		final int length = buf.length();

		for (final Map.Entry<String, String> entry : request.getParams().entrySet()) {
			validate("parameter", paramDefs, entry);
			if (length == buf.length()) {
				buf.append('?');
			} else {
				buf.append('&');
			}

			try {
				buf.append(entry.getKey())
					.append('=')
					.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
			} catch (final UnsupportedEncodingException uee) {
				throw new RestToolError(HttpStatus.BAD_REQUEST, uee, "Invalid parameter {0} encoding", 
						entry.getKey());
			}
		}

		return buf;
	}

	public HttpHeaders getRestHeaders(final ApiRequest request) throws RestToolError {
		final HttpSupport support = this.supports.get(request.getApiIndex());
		final HttpFunction func = support.getFuncs().get(request.getFuncIndex());
		final List<HttpField> headers = func.getHeaders();
		final HttpHeaders h = new HttpHeaders();

		for (final Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
			validate("header", headers, entry);
			h.add(entry.getKey(), entry.getValue());
		}

		return h;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getRestBody(final ApiRequest request) throws RestToolError {
		final HttpSupport support = this.supports.get(request.getApiIndex());
		final HttpFunction func = support.getFuncs().get(request.getFuncIndex());
		final List<HttpField> fields = func.getBody();
		final Map<String, Object> body = new HashMap<>();

		for (final Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
			validate("body field", fields, entry);

			final String[] names = entry.getKey().split(".");
			final int last = names.length - 1;
			Map<String, Object> obj = body;

			for (int index = 0; index < last; ++index) {
				obj = (Map<String, Object>) obj.get(names[index]);
			}
			obj.put(names[last], entry.getValue());
		}

		return body;
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

    public static boolean exists(final String value) {
    	return value != null && !value.isEmpty();
    }

	void validate(final String typeName, final List<HttpField> fields, 
			final Map.Entry<String, String> entry) throws RestToolError {
		final HttpField field = fields
			.stream()
			.filter(f -> f.getName().equals(entry.getKey()))
			.findFirst()
			.orElseThrow(() -> new RestToolError(HttpStatus.BAD_REQUEST, "Invalid {0} name {1}", 
					typeName, entry.getKey()));

		if (field.isRequired() && entry.getValue() == null) {
			new RestToolError(HttpStatus.BAD_REQUEST, "Invalid {0} name {1}; the field is required but " 
					+ "no value has been provided", 
				typeName, entry.getKey());
		}
		if (entry.getValue() != null && field.getPattern() != null 
				&& !entry.getValue().matches(field.getPattern())) {
			new RestToolError(HttpStatus.BAD_REQUEST, "Invalid {0} name {1}; value does not comply with " 
						+ "regex {2}", 
					typeName, entry.getKey(), (int) field.getMin(), entry.getValue().length(), 
					field.getPattern());
		}

		switch (field.getType()) {
		case TEXT:
			if (field.getMin() != null && field.getMin() > entry.getValue().length()) {
				new RestToolError(HttpStatus.BAD_REQUEST, "Invalid {0} name {1}; value length should be at " 
							+ "least {2} but it is {3}", 
						typeName, entry.getKey(), (int) field.getMin(), entry.getValue().length());
			}
			if (field.getMax() != null && field.getMax() > entry.getValue().length()) {
				new RestToolError(HttpStatus.BAD_REQUEST, "Invalid {0} name {1}; value length should be not " 
							+ "more than {2} but it is {3}", 
						typeName, entry.getKey(), (int) field.getMax(), entry.getValue().length());
			}

			break;

		default:
			// Nothing to do
		}
	}

} // end class HttpAllSupports
