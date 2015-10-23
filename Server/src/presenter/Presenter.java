package presenter;

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
	
	public Presenter(MyModel model, MyView ui) { 
		this.model = model;
		this.ui = ui;
		map = new HashMap<String, Command>();
		map.put("solve", new Solve(this.ui,this.model));
		map.put("start", new Start(this.ui,this.model));
		map.put("stop", new Stop(this.ui,this.model));
		
	}
	@Override
	public void update(Observable o, Object obj) {
		if (o == model) { 
			if(obj instanceof String) 
				ui.printString((String)obj);
		}
		else if(o == ui) {
			this.manipulateInput(obj);
		}
		
	}
	public void manipulateInput(Object obj) {
		if(obj instanceof Maze3d)
			map.get("Solve").doCommand(obj);
		if(obj instanceof String) { 
			switch ((String)obj) {
			case "start" : map.get("start").doCommand(obj);
				break;
			case "stop" : map.get("stop").doCommand(obj); 
			} 
		}
	}
}
