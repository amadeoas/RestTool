package co.uk.bocaditos.resttool.model;

import java.util.List;

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
	@JsonProperty(value="items", required=false)
	private List<HttpField> items;
	@JsonProperty(value="error", required=false)
	private String error;


	public String getName() {
		return this.name;
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
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpInputType getType() {
		return this.type;
	}

	public void setType(HttpInputType type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPattern() {
		return this.pattern;
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
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<HttpField> getItems() {
		return this.items;
	}

	public void setItems(List<HttpField> items) throws ViewModelException {
		if (HttpInputType.ARRAY != this.type) {
			throw new ViewModelException("Invalid type {0} when it should be ARRAY as it has ithems", 
					this.type);
		}

		this.items = items;
	}

} // HttpField
