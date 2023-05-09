package co.uk.bocaditos.resttool.model;

import java.util.List;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Representation of a request.
 * 
 * @author aasco
 */
public class HttpFunction {

	/**
	 * The query text.
	 */
	@JsonProperty(value="query", required=false)
	private String query;

	/**
	 * The HTTP method. It is required.
	 */
	@JsonProperty(value="method", required=true)
	private HttpMethod method;

	/**
	 * Information about the function, not required.
	 */
	@JsonProperty(value="info", required=false)
	private String info;

	/**
	 * The HTTP request headers.
	 */
	@JsonProperty(value="headers", required=true)
	private List<HttpField> headers;

	/**
	 * The HTTP request query parameters, if any.
	 */
	@JsonProperty(value="params", required=false)
	private List<HttpField> params;

	/**
	 * The HTTP request body, if any.
	 */
	@JsonProperty(value="body", required=false)
	private List<HttpField> body;


	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<HttpField> getHeaders() {
		return headers;
	}

	public void setHeaders(List<HttpField> headers) {
		this.headers = headers;
	}

	public List<HttpField> getParams() {
		return params;
	}

	public void setParams(List<HttpField> params) {
		this.params = params;
	}

	public List<HttpField> getBody() {
		return body;
	}

	public void setBody(List<HttpField> body) {
		this.body = body;
	}

} // end class HttpFunction
