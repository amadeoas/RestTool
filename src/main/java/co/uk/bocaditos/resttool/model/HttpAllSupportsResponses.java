package co.uk.bocaditos.resttool.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Represent all the supports and selected one.
 * 
 * @author aasco
 */
public class HttpAllSupportsResponses extends HttpAllSupports {

	private static final String NAME = "REST APIs UI";

	private List<List<String>> responses;


	public HttpAllSupportsResponses(final ObjectMapper mapper) throws IOException, ViewModelException {
		super(NAME, mapper);

		this.responses = new ArrayList<>(getSupports().size());
		for (int index = 0; index < getSupports().size(); ) {
			final HttpSupport support = get(index++);
			final List<String> resps = new ArrayList<>();

			for (int respIndex = 0; respIndex < support.getFuncs().size(); ++respIndex) {
				final String response 
						= loadResponeJson("option_" + index + "_response_" + (++respIndex) + ".json");

				resps.add(response);
			}
			this.responses.add(resps);
		}
	}
	
	public Object getResponse(final int apiIndex, final int funcIndex) {
		return responses.get(apiIndex).get(funcIndex);
	}

    private String loadResponeJson(final String filename) {
    	final ClassPathResource rsc = new ClassPathResource(filename);

    	try (final Stream<String> stream 
    			= Files.lines(Paths.get(rsc.getFile().toURI()), StandardCharsets.UTF_8)) {
    		final StringBuilder buf = new StringBuilder();
    		
    		stream.forEach(s -> buf.append(s).append("\n"));

    		return buf.toString();
    	} catch (final IOException ioe) {
    		return null;
    	}
    }

} // end class HttpAllSupportsResponses
