package co.uk.bocaditos.resttool.model;

import java.util.List;

import org.springframework.http.HttpMethod;


/**
 * Representation of a request.
 * 
 * @author aasco
 */
public class HttpFunction {

	/**
	 * .
	 */
	private String query;

	/**
	 * The HTTP method. It is required.
	 */
	private HttpMethod method;

	/**
	 * Information about the function, not required.
	 */
	private String info;

	/**
	 * The HTTP request headers.
	 */
	private List<HttpField> headers;

	/**
	 * The HTTP request query parameters, if any.
	 */
	private List<HttpField> params;

	/**
	 * The HTTP request body, if any.
	 */
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
