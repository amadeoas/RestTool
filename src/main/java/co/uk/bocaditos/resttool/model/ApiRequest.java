package co.uk.bocaditos.resttool.model;

import java.util.Map;


/**
 * Generic request.
 * 
 * @author aasco
 */
public class ApiRequest {

	private int apiIndex;
	private int envIndex;
	private int funcIndex;
	private Map<String, String> headers;
	private Map<String, String> params;
	private Map<String, String> body;


	public int getApiIndex() {
		return apiIndex;
	}

	public void setApiIndex(int apiIndex) {
		this.apiIndex = apiIndex;
	}

	public int getEnvIndex() {
		return envIndex;
	}

	public void setEnvIndex(int envIndex) {
		this.envIndex = envIndex;
	}

	public int getFuncIndex() {
		return funcIndex;
	}

	public void setFuncIndex(int funcIndex) {
		this.funcIndex = funcIndex;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getBody() {
		return body;
	}

	public void setBody(Map<String, String> body) {
		this.body = body;
	}

} // end class ApiRequest
