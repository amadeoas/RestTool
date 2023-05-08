package co.uk.bocaditos.resttool.model;


/**
 * Representation of a field.
 * 
 * @author aasco
 */
public class HttpField {
	
	private String name;
	private HttpInputType type;
	private String pattern;
	private Integer min; // minimum value for numbers and minimum length for text
	private Integer max; // maximum value for numbers and maximum length for text
	private boolean required;
	private boolean disabled;
	private String error;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HttpInputType getType() {
		return type;
	}

	public void setType(HttpInputType type) {
		this.type = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Integer getMin() {
		return this.min;
	}

	public void setMin(final Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return this.max;
	}

	public void setMax(final Integer max) {
		this.max = max;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

} // HttpField
