package presenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeClientHandler extends Observable implements Runnable {
	
	private Socket someClient;
	private Object obj;
	
	public MazeClientHandler(Socket someClient, Presenter p) { 
		this.someClient = someClient;
	}
	@Override
	public void run() {
		Object obj = extractObject();
		if(obj != null) {
			this.obj = obj;
			this.setChanged();
			this.notifyObservers(this.obj);
		}
	}
	private Object extractObject() { 
		try { 
			ObjectInputStream ois = new ObjectInputStream(someClient.getInputStream());
			Object obj = ois.readObject();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	public void sendSolutionToClient(Solution<Position> solution) {
		ObjectOutputStream bos;
		try {
			bos = new ObjectOutputStream(someClient.getOutputStream());
			bos.writeObject(solution);
			bos.flush();
			bos.close();
			System.out.println("The client " + someClient.getInetAddress().toString().substring(1) + " has disconnected");
			someClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Object getObj() {
		return obj;
	}
}
