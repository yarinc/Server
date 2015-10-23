package presenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyTCPIPServer {
	
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean isOn = false;
	private Presenter p;
	
	public MyTCPIPServer(int port, Presenter p){
		this.port = port;
		this.p = p;
	}

	public void startServer(int numOfClients){
		isOn = true;
		p.print("The Server is now up and waiting for connections.");
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			server=new ServerSocket(this.port);
			while(isOn){
				Socket someClient = server.accept();
				p.print("The client " + someClient.getInetAddress().toString().substring(1) + " is now connected");
				
				MazeClientHandler client = new MazeClientHandler(someClient,p);
				client.addObserver(p);
				executer.execute(new Thread(client));
			}
		} catch (SocketException e) {
		} catch(IOException e) {
			p.print("Error at stopping the server.");
		}
	}
	public void stopServer(){	
		// do not execute jobs in queue, continue to execute running threads
		p.print("shutting down");
		((ExecutorService)executer).shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		try {
			while(!((ExecutorService)executer).awaitTermination(10, TimeUnit.SECONDS));
		} catch (InterruptedException e1) {
			p.print("Server was shutdown without finish tasks.");
		} finally { 
			p.print("all the tasks have finished");
			try {
				server.close();
			} catch (Exception e) {
				p.print("tiered of waiting for connection");
			}finally {
				((ExecutorService)executer).shutdown();
				p.print("server is safely closed");
				isOn=false;
			}	
		}
	}
	
	public void exitServer() {
		if (isOn) 
			stopServer();
		p.print("Exiting server...");
	}
	public boolean isOn() { 
		return isOn;
	}
	
	public Executor getExecuter() {
		return executer;
	}
}
