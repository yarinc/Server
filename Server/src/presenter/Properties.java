package presenter;

import java.io.Serializable;

public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;
	private String SolutionAlgorithm;
	private int numOfClients;
	private int port;
	
	
	public void setSolutionAlgorithm(String solutionAlgorithm) {
		SolutionAlgorithm = solutionAlgorithm;
	}
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	public String getSolutionAlgorithm() {
		return SolutionAlgorithm;
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
}
