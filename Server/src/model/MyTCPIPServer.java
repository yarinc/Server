package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyTCPIPServer {
	
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean isOn = false;
	private MyModel model;
	
	public MyTCPIPServer(int port, MyModel model){
		this.port = port;
		this.model = model;
	}

	public void startServer(int numOfClients){
		isOn = true;
		System.out.println("The Server is now up and waiting for connections.");
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			server=new ServerSocket(this.port);
			while(isOn){
				Socket someClient = server.accept();
				System.out.println("The client " + someClient.getInetAddress().toString().substring(1) + " is now connected");
				MazeClientHandler client = new MazeClientHandler(someClient,model);
				executer.execute(new Thread(client));
			}
			server.close();
		} catch (Exception e) {
			System.out.println("tiered of waiting for connection");
		}finally {
			((ExecutorService)executer).shutdown();
		}		
	}
	public void stopServer(){
		isOn = false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOn() { 
		return isOn;
	}
	
	public Executor getExecuter() {
		return executer;
	}
}
