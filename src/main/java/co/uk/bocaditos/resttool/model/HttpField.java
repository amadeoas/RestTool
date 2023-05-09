package co.uk.bocaditos.resttool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a field.
 * 
 * @author aasco
 */
public class HttpField {

	@JsonProperty(value="name", required=true)
	private String name;
	@JsonProperty(value="path", required=true)
	private String path; // Name of all fields path, i.e. <field_name1>[.<field_name2>...]
	@JsonProperty(value="type", required=true)
	private HttpInputType type;
	@JsonProperty(value="value", required=false)
	private String value;
	@JsonProperty(value="pattern", required=false)
	private String pattern;
	@JsonProperty(value="min", required=false)
	private Integer min; // minimum value for numbers and minimum length for text
	@JsonProperty(value="max", required=false)
	private Integer max; // maximum value for numbers and maximum length for text
	@JsonProperty(value="required", required=false)
	private boolean required;
	@JsonProperty(value="disabled", required=false)
	private boolean disabled;
	@JsonProperty(value="info", required=false)
	private String info;
	@JsonProperty(value="error", required=false)
	private String error;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpInputType getType() {
		return type;
	}

	public void setType(HttpInputType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
