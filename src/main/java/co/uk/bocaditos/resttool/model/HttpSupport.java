package co.uk.bocaditos.resttool.model;

import java.util.List;


/**
 * Representation for all the support provided by an API.
 * 
 * @author aasco
 */
public class HttpSupport {

	/**
	 * API name. It's required.
	 */
	private String name;

	/**
	 * Information about the API. It isn't required.
	 */
	private String info;

	/**
	 * The environment index to be display as default;
	 */
	private int envIndex;

	/**
	 * The function index to be display as default;
	 */
	private int funcIndex;

	/**
	 * Available environments.
	 */
	private List<HttpEnvironment> envs;

	/**
	 * Available functionality for this API
	 */
	private List<HttpFunction> funcs;


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