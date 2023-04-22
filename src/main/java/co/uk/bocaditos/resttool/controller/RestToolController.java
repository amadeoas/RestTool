package co.uk.bocaditos.resttool.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class RestToolController {

	private static final Logger logger = LogManager.getLogger(RestToolController.class);


	public RestToolController() {
	}

    @GetMapping({"/", "/home"})
    public String home(final Model model) {
    	logger.debug("Setting up home page...");
    	logger.debug("Home page was successfully set up");

        return "main";
    }

    @GetMapping("/option1")
    public String option1(final Model model) {
    	logger.debug("Setting up Option 1 page...");
    	logger.debug("Option 1 page was successfully set up");

        return "option_1";
    }

    @GetMapping("/option2")
    public String option2(final Model model) {
    	logger.debug("Setting up Option 2 page...");
    	logger.debug("Option 2 page was successfully set up");

        return "option_2";
    }

    @GetMapping("/option3")
    public String option3(final Model model) {
    	logger.debug("Setting up Option 3 page...");
    	logger.debug("Option 3 page was successfully set up");

        return "option_3";
    }

    @GetMapping("/option4")
    public String option4(final Model model) {
    	logger.debug("Setting up Option 4 page...");
    	logger.debug("Option 4 page was successfully set up");

        return "option_4";
    }

    @GetMapping({"/login"})
    public String login(final Model model) {
    	logger.debug("Setting up login page...");
    	logger.debug("Login page was successfully set up");

    	return "login_page";
    }

    @GetMapping({"/licence"})
    public String lice(final Model model) {
        return "licence_page";
    }

} // end class RestToolController
