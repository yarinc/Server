package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeClientHandler extends Observable implements Runnable {
	
	private Socket someClient;
	private MyModel model;
	
	public MazeClientHandler(Socket someClient, MyModel model) { 
		this.someClient = someClient;
		this.model = model;
	}
	@Override
	public void run() {
		Object obj = extractObject();
		if(obj != null) {
			Solution<Position> answer = model.solveMaze((Maze3d)obj);
			sendSolutionToClient(answer);
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
}
