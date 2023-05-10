package co.uk.bocaditos.resttool.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.uk.bocaditos.resttool.model.HttpAllSupportsResponses;
import co.uk.bocaditos.resttool.model.ViewModelException;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class RestToolController {

	private static final Logger logger = LogManager.getLogger(RestToolController.class);

	private final HttpAllSupportsResponses supported;


	public RestToolController(final HttpAllSupportsResponses supported) 
			throws IOException, ViewModelException {
		this.supported = supported;
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
