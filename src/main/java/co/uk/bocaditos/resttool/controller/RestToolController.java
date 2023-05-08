package co.uk.bocaditos.resttool.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.bocaditos.resttool.config.service.ProcessService;
import co.uk.bocaditos.resttool.model.ApiRequest;
import co.uk.bocaditos.resttool.model.HttpAllSupports;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class RestToolController {

	private static final Logger logger = LogManager.getLogger(RestToolController.class);
	private static final String NAME = "REST APIs UI Tool";

	private final HttpAllSupports supported;
	
	private final ProcessService service;


	/**
	 * Represent all the supports and selected one.
	 * 
	 * @author aasco
	 */
	public class HttpAllSupportsIndex extends HttpAllSupports {

		private int index;


		public HttpAllSupportsIndex(final HttpAllSupports supported, final int index) {
			super(supported);
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}

	} // end class HttpAllSupportsIndex()


	public RestToolController(final ObjectMapper mapper, final ProcessService service) throws IOException {
		this.service = service;
		this.supported = new HttpAllSupports(NAME, mapper);
	}

	@GetMapping({"/", "/home"})
    public String home(final Model model) {
        return option(model, -1);
    }

    @GetMapping(value = "/option1")
    public String option1(final Model model) {
        return option(model, 0);
    }

    @GetMapping(value = "/option2")
    public String option2(final Model model) {
        return option(model, 1);
    }

    @GetMapping("/option3")
    public String option3(final Model model) {
        return option(model, 2);
    }

    @GetMapping("/option4")
    public String option4(final Model model) {
        return option(model, 3);
    }

    @GetMapping("/login")
    public String login(final Model model) {
        return option(model, this.supported.getNumApis());
    }

    @PostMapping("/execute")
    public Object perform(@RequestBody final ApiRequest request) throws RestToolError {
    	return this.service.process(this.supported.getMethod(request), this.supported.getRestHost(request), 
    			this.supported.getRestHeaders(request), this.supported.getRestBody(request));
    }
    
    private String option(final Model model, final int index) {
    	logger.debug("Setting up {} page...", getApiName(index));

    	model.addAttribute("apiName", this.supported.getName());
    	model.addAttribute("data", this.supported.toString());
    	model.addAttribute("index", index);
    	logger.debug("{} page was successfully set up", getApiName(index));

        return "option";
    }

    private String getApiName(final int index) {
    	return (index < 0) 
    			? "Home" : ((index >= this.supported.getNumApis()) 
    					? "Login" : this.supported.get(index).getName());
    }

} // end class RestToolController
