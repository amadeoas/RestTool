package co.uk.bocaditos.resttool.controller;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;


@SuppressWarnings("serial")
public class RestToolError extends Exception {

	private HttpStatus status;


	public RestToolError(final HttpStatus status, final String msg, final Object... params) {
		super(MessageFormat.format(msg, params));

		this.status = status;
	}
	public RestToolError(final HttpStatus status, final Throwable cause, final String msg, 
			final Object... params) {
		super(MessageFormat.format(msg, params), cause);

		this.status = status;
	}

	public final HttpStatus getStatus() {
		return this.status;
	}

} // end class RestToolError
