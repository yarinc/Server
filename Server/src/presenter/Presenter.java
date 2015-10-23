package presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.MyModel;
import view.MyView;

public class Presenter implements Observer {
	private MyModel model;
	private MyView ui;
	private HashMap<String,Command> map;
	private MyTCPIPServer server;
	private ArrayList<MazeClientHandler> clients;
	
	public void setServer(MyTCPIPServer server) {
		this.server = server;
	}
	public Presenter(MyModel model, MyView ui) { 
		this.model = model;
		this.ui = ui;
		map = new HashMap<String, Command>();
		map.put("solve", new Solve(this.ui,this.model));
		clients = new ArrayList<MazeClientHandler>();
	}
	@Override
	public void update(Observable o, Object obj) {
		if (o == model) { 
			if(obj instanceof String) 
				ui.printString((String)obj);
			if(obj instanceof MazeToSolution) { 
				for(int i=0;i<clients.size();i++) {
					if((clients.get(i).getObj() instanceof Maze3d) && ((MazeToSolution)obj).getMaze().equals(clients.get(i).getObj())) {
						clients.get(i).sendSolutionToClient(((MazeToSolution)obj).getSolution());
						clients.remove(i);
					}
				}
			}
		}
		else if(o == ui) {
			this.manipulateInput(obj);
		}
		else if(o instanceof MazeClientHandler) {
			clients.add((MazeClientHandler)o);
			this.manipulateInput(obj);
		}
		
	}

	public void manipulateInput(Object obj) {
		if(obj instanceof Maze3d) {
			map.get("solve").doCommand(obj);
		}
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
	public void print(String string) { 
		ui.printString(string);
	}
}
