package presenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Class MyTCPIPServer responsible for listening for request from the client and forward them.
 */
public class MyTCPIPServer {
	
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean isOn = false;
	private Presenter p;
	
	/**
	 * Instantiates a new TCPIP server.
	 * @param port the port to listen
	 * @param p the presenter
	 */
	public MyTCPIPServer(int port, Presenter p){
		this.port = port;
		this.p = p;
	}

	/**
	 * Start server functionality
	 * @param numOfClients the maximum number of clients to serve at the same time.
	 */
	public void startServer(int numOfClients){
		//Set flag to on and print a message.
		isOn = true;
		p.print("The Server is now up and waiting for connections.");
		//Define a thread pool.
		executer = Executors.newFixedThreadPool(numOfClients);
		try {
			//Start the server to listen to requests.
			server=new ServerSocket(this.port);
			//While server is on
			while(isOn){
				//Wait for a client to connect. and prints he is connected to the server.
				Socket someClient = server.accept();
				p.print("The client " + someClient.getInetAddress().toString().substring(1) + " is now connected");
				//Create a MazeClientHandler and add the presenter as observer.
				MazeClientHandler client = new MazeClientHandler(someClient,p);
				client.addObserver(p);
				//Run the client at a diffrent thread in the thread pool.
				executer.execute(new Thread(client));
			}
		} catch (SocketException e) {
		} catch(IOException e) {
			p.print("Error at stopping the server.");
		}
	}
	
	/**
	 * Stop server functionality.
	 */
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
				//close the server.
				server.close();
			} catch (Exception e) {
				p.print("tiered of waiting for connection");
			}finally {
				((ExecutorService)executer).shutdown();
				p.print("server is safely closed");
				//Set flag to off.
				isOn=false;
			}	
		}
	}
	
	/**
	 * Exit from server.
	 */
	public void exitServer() {
		//If server is on - stop it.
		if (isOn) 
			stopServer();
		p.print("Exiting server...");
	}
	
	/**
	 * Checks if the server is on.
	 * @return true, if is on
	 */
	public boolean isOn() { 
		return isOn;
	}
}
