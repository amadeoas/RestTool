package co.uk.bocaditos.resttool.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Representation for all the support provided by an API.
 * 
 * @author aasco
 */
public class HttpSupport {

	/**
	 * API name. It's required.
	 */
	@JsonProperty(value="name", required=true)
	private String name;

	/**
	 * Information about the API. It isn't required.
	 */
	@JsonProperty(value="info", required=false)
	private String info;

	/**
	 * The environment index to be display as default;
	 */
	@JsonProperty(value="envIndex", required=true)
	private int envIndex;

	/**
	 * The function index to be display as default;
	 */
	@JsonProperty(value="funcIndex", required=true)
	private int funcIndex;

	/**
	 * Available environments.
	 */
	@JsonProperty(value="envs", required=true)
	private List<HttpEnvironment> envs;

	/**
	 * Available functionality for this API
	 */
	@JsonProperty(value="funcs", required=true)
	private List<HttpFunction> funcs;


	public void validate() throws ViewModelException {
		if (this.envs == null || this.envs.size() == 0) {
			throw new ViewModelException("No environments provided");
		}

		if (this.funcs == null || this.funcs.size() == 0) {
			throw new ViewModelException("No functions provided");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<HttpEnvironment> getEnvs() {
		return envs;
	}

	public void setEnvs(List<HttpEnvironment> envs) {
		this.envs = envs;
	}

	public List<HttpFunction> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<HttpFunction> funcs) {
		this.funcs = funcs;
	}

	public int getEnvIndex() {
		return envIndex;
	}

	public void setEnvIndex(int envIndex) {
		this.envIndex = envIndex;
	}

	public int getFuncIndex() {
		return funcIndex;
	}

	public void setFuncIndex(int funcIndex) {
		this.funcIndex = funcIndex;
	}

} // end class HttpSupport
