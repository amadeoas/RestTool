package co.uk.bocaditos.transtrack.model;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * .
 * 
 * @author aasco
 */
public class ResponseModel {

	private static final Logger logger = LogManager.getLogger(ResponseModel.class);

	/**
	 * The version with format: M.m.ppp that corresponds to 'M' major, 'm' minor and 'ppp' patch.
	 */
	private final String version; // the version of the data
	/**
	 * The name of this tool.
	 */
	private final String name;
//	/**
//	 * JSON representation of the instance of this class.
//	 */
//	private String json; // JSON form of the full object 
	private final int w_0;
	private final int w_max;
	private final int w_rect;
	private final int h;
	private final int minHeight;
	private final String requestReceived;
	private final String requestResponse;
	private final String sendReques;
	private final String sendResponse;
	private final String flowView_top;


	public ResponseModel(@Value("${app.name}") final String name, 
			@Value("${app.version}") final String version, final ObjectMapper mapper,
			@Autowired final Environment env) throws IOException, ViewModelException {
		logger.info("{} v{} loading supported APIs...", name, version);
		this.name = name;
		this.version = version;
//		this.json = mapper.writeValueAsString(this);
		this.w_0 = getProperty(env, "w_0", 50);
		this.w_max = getProperty(env, "w.max", 200);
		this.w_rect = getProperty(env, "w.rect", 20);
		this.h = getProperty(env, "h", 20);
		this.minHeight = getProperty(env, "minHeight", 2);
		this.requestReceived = env.getProperty("requestReceived", "REQUEST RECEIVED");
		this.requestResponse = env.getProperty("requestResponse", "REQUEST RESPONSE");
		this.sendReques = env.getProperty("sendReques", "SEND REQUEST");
		this.sendResponse = env.getProperty("sendResponse", "SEND RESPONSE");
		this.flowView_top = env.getProperty("flowView.top", "40px");
		logger.info("{} v{}", name, this.version);
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}
//
//	public String getJson() {
//		return json;
//	}

	public int getW0() {
		return w_0;
	}

	public int getWMax() {
		return w_max;
	}

	public int getWRect() {
		return w_rect;
	}

	public int getH() {
		return h;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public String getRequestReceived() {
		return requestReceived;
	}

	public String getRequestResponse() {
		return requestResponse;
	}

	public String getSendReques() {
		return sendReques;
	}

	public String getSendResponse() {
		return sendResponse;
	}

	public String getFlowViewTop() {
		return flowView_top;
	}

	private static int getProperty(final Environment env, final String name, final int defaultValue) {
		final String value = env.getProperty("ui." + name);

		if (value == null || value.isEmpty()) {
			return defaultValue;
		}

		return Integer.parseInt(value);
	}

} // end class ResponseModel
