package co.uk.bocaditos.resttool.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.bocaditos.resttool.model.HttpAllSupports;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class RestToolController {

	private static final Logger logger = LogManager.getLogger(RestToolController.class);

	private final HttpAllSupports supported;


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


	public RestToolController(final ObjectMapper mapper) throws IOException {
		this.supported = new HttpAllSupports(mapper);
	}

	@GetMapping({"/", "/home"})
    public String home(final Model model) {
    	logger.debug("Setting up home page...");
    	model.addAttribute("data", new HttpAllSupportsIndex(this.supported, -1));
    	logger.debug("Home page was successfully set up");

        return "main";
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
    	logger.debug("Setting up login page...");
    	logger.debug("Login page was successfully set up");

    	return "login_page";
    }
    
    private String option(final Model model, final int index) {
    	logger.debug("Setting up {} page...", this.supported.get(index).getName());
    	model.addAttribute("data", new HttpAllSupportsIndex(this.supported, index));
    	logger.debug("{} page was successfully set up", this.supported.get(index).getName());

        return "option";
    }

} // end class RestToolController
