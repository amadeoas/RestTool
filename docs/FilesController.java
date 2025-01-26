package co.uk.bocaditos.transtrack.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.uk.bocaditos.transtrack.format.FormatException;
import co.uk.bocaditos.transtrack.format.LogFormat;
import co.uk.bocaditos.transtrack.format.model.Logs;
import co.uk.bocaditos.transtrack.model.files.FileContent;
import co.uk.bocaditos.transtrack.model.files.FilesData;
import co.uk.bocaditos.transtrack.service.ApiException;
import co.uk.bocaditos.transtrack.service.InternalServerException;


/**
 * Support to process data from files.
 * 
 * @author aasco
 */
@RestController("/records")
public class FilesController {

	private static final String[] formats = {
			"[{datetime}] [{artifact}] [{path}] - ::{reqStartType}:: {msg} ::{reqEndType}::",
			"[{datetime}] [{artifact}] [{path}] - {msg}"};
	private static LogFormat format;

	static {
		try {
			FilesController.format = new LogFormat(FilesController.formats);
		} catch (FormatException fe) {
			throw new RuntimeException("Failed to load formats", fe);
		}
	}


	@CrossOrigin
	@PostMapping(value = "/", consumes="application/json", produces="application/json")
	public Logs uploadFileHandler(@RequestBody final FilesData filesData) throws ApiException {
		final Logs logs = new Logs(filesData.getFrom(), filesData.getTo());
		final LogFormat format = (filesData.getFormats() == null) 
				? FilesController.format : new LogFormat(filesData.getFormats());
		FileContent fileContent = null;
		String line;

		try {
			for (int index = 0; index < filesData.getContents().size(); ++index) {
				fileContent = filesData.getContents().get(index);
	
				final BufferedReader reader 
						= new BufferedReader(new StringReader(fileContent.getContent()));
	
				while ((line = reader.readLine()) != null) {
					if (!logs.add(format, line)) {
						break;
					}
			    }
			}
		} catch (final IOException ioe) {
			throw new InternalServerException(ioe, "Failed to load file \"{0]\"", 
					fileContent.getFile());
		}

		return logs;
	}

} // end class FilesController
