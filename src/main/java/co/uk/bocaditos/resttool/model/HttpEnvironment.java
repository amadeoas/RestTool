package co.uk.bocaditos.resttool.model;


/**
 * Representation of the environment where API is running.
 * 
 * @author aasco
 */
public class HttpEnvironment {
	
	/**
	 * The environment name.
	 */
	private String name;

	/**
	 * The starting part of the URL, location of the running API.
	 */
	private String route;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

} // end class HttpEnvironment
