package co.uk.bocaditos.resttool.model;

import java.text.MessageFormat;


/**
 * .
 * 
 * @author aasco
 */
@SuppressWarnings("serial")
public class ViewModelException extends Exception {

	public ViewModelException(final String msg, final Object... params) {
		this(null, msg, params);
	}

	public ViewModelException(final Throwable cause, final String msg, 
			final Object... params) {
		super(MessageFormat.format(msg, params), cause);
	}

} // end class ViewModelException
