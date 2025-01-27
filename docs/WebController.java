package co.uk.bocaditos.transtrack.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.uk.bocaditos.transtrack.model.ResponseModel;


/**
 * .
 * 
 * @author aasco
 */
@Controller
public class WebController {

	private static final Logger logger = LogManager.getLogger(WebController.class);

	private final ResponseModel supported;


	public WebController(@Autowired final ResponseModel supported) {
		this.supported = supported;
	}

	@GetMapping({"/", "/home"})
    public String home(final Model model) throws IOException {
    	logger.debug("Setting up Home page...");

    	model.addAttribute("uiName", this.supported.getName());
    	model.addAttribute("version", this.supported.getVersion());
    	model.addAttribute("w_0", this.supported.getW0());
    	model.addAttribute("w_max", this.supported.getWMax());
    	model.addAttribute("w_rect", this.supported.getWRect());
    	model.addAttribute("h", this.supported.getH());
    	model.addAttribute("minHeight", this.supported.getMinHeight());
    	model.addAttribute("requestReceived", this.supported.getRequestReceived());
    	model.addAttribute("requestResponse", this.supported.getRequestResponse());
    	model.addAttribute("sendReques", this.supported.getSendReques());
    	model.addAttribute("sendResponse", this.supported.getSendResponse());
    	model.addAttribute("flowView_top", this.supported.getFlowViewTop());
    	model.addAttribute("logs", loadFile());
    	logger.debug("Home page was successfully set up");

        return "option";
    }

    @GetMapping("/login")
    public String login(final Model model) {
		logger.debug("Requested login page");

		return "option";
    }

    private static String loadFile() throws IOException {
    	final StringBuilder buf = new StringBuilder();

		try (final BufferedReader br = new BufferedReader(new FileReader("docs/example.json"))) {
		    String line;

		    while ((line = br.readLine()) != null) {
		       if (buf.length() > 0) {
		    	   buf.append('\n');
		       }
		       buf.append(line);
		    }
		}

    	return buf.toString();
    }

} // end class RestToolController
