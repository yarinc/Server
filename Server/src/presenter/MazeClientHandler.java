package presenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class MazeClientHandler handle with all communication with the client he represent.
 */
public class MazeClientHandler extends Observable implements Runnable {
	
	private Socket someClient;
	private Object obj;
	
	/**
	 * Instantiates a new MazeClientHandler.
	 * @param someClient the client
	 * @param p the presenter
	 */
	public MazeClientHandler(Socket someClient) { 
		this.someClient = someClient;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Object obj = extractObject();
		if(obj != null) {
			this.obj = obj;
			this.setChanged();
			this.notifyObservers(this.obj);
		}
	}
	
	/**
	 * Extract object from the inputStream.
	 * @return the object extracted.
	 */
	private Object extractObject() { 
		try { 
			//Use ObjectInputStream to read he object and return it.
			ObjectInputStream ois = new ObjectInputStream(someClient.getInputStream());
			Object obj = ois.readObject();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Send the solution back to the client.
	 * @param solution the solution
	 */
	public void sendSolutionToClient(Solution<Position> solution) {
		try {
			//Use ObjectOutputStream to write the solution back to the client. 
			ObjectOutputStream bos = new ObjectOutputStream(someClient.getOutputStream());
			bos.writeObject(solution);
			bos.flush();
			bos.close();
			//Printing a message that the client has disconnected.
			System.out.println("The client " + someClient.getInetAddress().toString().substring(1) + " has disconnected");
			//Close the connection.
			someClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the object.
	 * @return the object
	 */
	public Object getObj() {
		return obj;
	}
}
