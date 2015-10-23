package presenter;

import java.io.Serializable;

/**
 * The Class Properties represent all the settings available to change for the server via XML file.
 */
public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;
	private String solutionAlgorithm;
	private String cacheMazes;
	private int numOfClients;
	private int port;
	
	
	/**
	 * Sets the solution algorithm.
	 * @param solutionAlgorithm the new solution algorithm
	 */
	public void setSolutionAlgorithm(String solutionAlgorithm) {
		this.solutionAlgorithm = solutionAlgorithm;
	}
	
	/**
	 * Sets the number of clients.
	 * @param numOfClients the new number of clients
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	
	/**
	 * Gets the solution algorithm.
	 * @return the solution algorithm
	 */
	public String getSolutionAlgorithm() {
		return solutionAlgorithm;
	}
	
	/**
	 * Gets the number of clients.
	 * @return the number of clients
	 */
	public int getNumOfClients() { 
		return numOfClients;
	}
	
	/**
	 * Gets the port.
	 * @return the port
	 */
	public int getPort() { 
		return port;
	}
	
	/**
	 * Sets the port.
	 * @param port the new port
	 */
	public void setPort(int port) { 
		this.port = port;
	}
	
	/**
	 * Gets the cache mazes path.
	 * @return the cache mazes path
	 */
	public String getCacheMazes() {
		return cacheMazes;
	}
	
	/**
	 * Sets the cache mazes path.
	 * @param cacheMazes the new cache mazes path
	 */
	public void setCacheMazes(String cacheMazes) {
		this.cacheMazes = cacheMazes;
	}
}
