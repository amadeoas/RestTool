package co.uk.bocaditos.resttool.config.service.rest;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.uk.bocaditos.resttool.config.service.ProcessService;
import co.uk.bocaditos.resttool.controller.RestToolError;


/**
 * .
 * 
 * @author aasco
 */
@Service
public class RestProcessService implements ProcessService {

	private final RestTemplate restTemplate;


	public RestProcessService(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ?> process(final HttpMethod method, final String host, final HttpHeaders headers, 
			final Map<String, ?> body) throws RestToolError {
		if (HttpMethod.HEAD == method || HttpMethod.OPTIONS == method || HttpMethod.TRACE == method) {
			throw new RestToolError(HttpStatus.BAD_REQUEST, "Invalid method {0}", method);
		}

		final HttpEntity<Map<String, ?>> request = new HttpEntity<>(body, headers);
		
		return (Map<String, ?>) this.restTemplate.exchange(host, method, request, Map.class);
	}

} // end lass RestProcessService
