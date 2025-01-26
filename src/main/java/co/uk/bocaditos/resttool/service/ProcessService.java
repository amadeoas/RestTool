package co.uk.bocaditos.resttool.service;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import co.uk.bocaditos.resttool.controller.RestToolError;


/**
 * .
 * 
 * @author aasco
 */
public interface ProcessService {

	public Map<String, ?> process(HttpMethod method, String host, HttpHeaders headers, 
			Map<String, ?> body) throws RestToolError;

} // end interface ProcessService
