package presenter;

import java.io.Serializable;

public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;
	private String solutionAlgorithm;
	private String cacheMazes;
	private int numOfClients;
	private int port;
	
	
	public void setSolutionAlgorithm(String solutionAlgorithm) {
		this.solutionAlgorithm = solutionAlgorithm;
	}
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	public String getSolutionAlgorithm() {
		return solutionAlgorithm;
	}
	public int getNumOfClients() { 
		return numOfClients;
	}
	public int getPort() { 
		return port;
	}
	public void setPort(int port) { 
		this.port = port;
	}
	public String getCacheMazes() {
		return cacheMazes;
	}
	public void setCacheMazes(String cacheMazes) {
		this.cacheMazes = cacheMazes;
	}
}
