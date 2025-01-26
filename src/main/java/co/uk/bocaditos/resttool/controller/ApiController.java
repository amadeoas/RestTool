package co.uk.bocaditos.resttool.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.uk.bocaditos.resttool.model.ApiRequest;
import co.uk.bocaditos.resttool.model.HttpAllSupportsResponses;
import co.uk.bocaditos.resttool.service.ProcessService;


/**
 * .
 * 
 * @author aasco
 */
@RestController
public class ApiController {
	
	private final HttpAllSupportsResponses supported;
	private final ProcessService service;


	public ApiController(final HttpAllSupportsResponses supported, final ProcessService service) {
		this.supported = supported;
		this.service = service;
	}

	@PostMapping(value = "/execute", produces = "application/json")
    public Object perform(@RequestBody final ApiRequest request) throws RestToolError {
    	final Object response = this.supported.getResponse(request.getApiIndex(), request.getFuncIndex());
    	
    	if (response != null) {
    		return response;
    	}

    	return this.service.process(this.supported.getMethod(request), this.supported.getRestHost(request), 
    			this.supported.getRestHeaders(request), this.supported.getRestBody(request));
    }

} // end class ApiController
