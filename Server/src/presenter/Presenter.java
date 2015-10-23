package presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.MyModel;
import view.MyView;

/**
 * The Class Presenter is a presenter in a MVP architecture.
 */
public class Presenter implements Observer {

	private MyModel model;
	private MyView ui;
	private HashMap<String,Command> map;
	private MyTCPIPServer server;
	private ArrayList<MazeClientHandler> clients;
	
	/**
	 * Sets the server attribute.
	 * @param server the new server
	 */
	public void setServer(MyTCPIPServer server) {
		this.server = server;
	}
	
	/**
	 * Instantiates a new presenter object.
	 * @param model the model
	 * @param ui the ui
	 */
	public Presenter(MyModel model, MyView ui) { 
		this.model = model;
		this.ui = ui;
		map = new HashMap<String, Command>();
		map.put("solve", new Solve(this.ui,this.model));
		clients = new ArrayList<MazeClientHandler>();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object obj) {
		//If the observable is model and the object is a string, print it.
		if (o == model) { 
			if(obj instanceof String) 
				ui.printString((String)obj);
			//If the object is a MazeToSolution, find the right client and return him the solution.
			if(obj instanceof MazeToSolution) { 
				for(int i=0;i<clients.size();i++) {
					if((clients.get(i).getObj() instanceof Maze3d) && ((MazeToSolution)obj).getMaze().equals(clients.get(i).getObj())) {
						clients.get(i).sendSolutionToClient(((MazeToSolution)obj).getSolution());
						//Remove client from client list.
						clients.remove(i);
					}
				}
			}
		}
		//If the observable is the ui, manipulate the input.
		else if(o == ui) {
			this.manipulateInput(obj);
		}
		//if the observable is MazeClientHandler, add it to the client list and manipulate the input.
		else if(o instanceof MazeClientHandler) {
			clients.add((MazeClientHandler)o);
			this.manipulateInput(obj);
		}
		
	}

	/**
	 * Manipulate input to active relevant action.
	 * @param obj the obj
	 */
	public void manipulateInput(Object obj) {
		//If obj is a Maze3d - solve it.
		if(obj instanceof Maze3d) {
			map.get("solve").doCommand(obj);
		}
		//If it's a string, find the relevant action.
		if(obj instanceof String) { 
			switch ((String)obj) {
			case "start" : server.startServer(model.getProperties().getNumOfClients());
				break;
			case "stop" : server.stopServer();
				break;
			case "exit" : server.exitServer();
			} 
		}
	}
	
	/**
	 * Prints sending the string to the user interface for displaying.
	 * @param string the string
	 */
	public void print(String string) { 
		ui.printString(string);
	}
}
