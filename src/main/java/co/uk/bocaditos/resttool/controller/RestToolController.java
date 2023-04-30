package co.uk.bocaditos.resttool.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class RestToolController {

	private static final Logger logger = LogManager.getLogger(RestToolController.class);


    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(final Model model) {
    	logger.debug("Setting up home page...");
    	logger.debug("Home page was successfully set up");

        return "main";
    }

    @RequestMapping(value = "/option1", method = RequestMethod.GET)
    public String option1(final Model model) {
    	logger.debug("Setting up Option 1 page...");
    	logger.debug("Option 1 page was successfully set up");

        return "option_1";
    }

    @RequestMapping(value = "/option2", method = RequestMethod.GET)
    public String option2(final Model model) {
    	logger.debug("Setting up Option 2 page...");
    	logger.debug("Option 2 page was successfully set up");

        return "option_2";
    }

    @RequestMapping(value = "/option3", method = RequestMethod.GET)
    public String option3(final Model model) {
    	logger.debug("Setting up Option 3 page...");
    	logger.debug("Option 3 page was successfully set up");

        return "option_3";
    }

    @RequestMapping(value = "/option4", method = RequestMethod.GET)
    public String option4(final Model model) {
    	logger.debug("Setting up Option 4 page...");
    	logger.debug("Option 4 page was successfully set up");

        return "option_4";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final Model model) {
    	logger.debug("Setting up login page...");
    	logger.debug("Login page was successfully set up");

    	return "login_page";
    }

    @RequestMapping(value = "/licence", method = RequestMethod.GET)
    public String lice(final Model model) {
        return "licence_page";
    }

} // end class RestToolController
