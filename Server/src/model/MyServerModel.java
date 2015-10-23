package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;

import algorithms.demo.Maze3dSearch;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.AirDistance;
import algorithms.search.BFS;
import algorithms.search.ManhattanDistance;
import algorithms.search.Solution;
import presenter.Properties;

public class MyServerModel extends Observable implements MyModel {
	
	private HashMap<Maze3d,Solution<Position>> cachedMazes;
	private Properties properties;
	private MyTCPIPServer server;
	
	public MyServerModel() {
		cachedMazes = new HashMap<Maze3d,Solution<Position>>();
		//Try to load XML from file.
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("resources\\properties.xml")));
			this.properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		server = new MyTCPIPServer(properties.getPort(), this);
		server.startServer(properties.getNumOfClients());
	}
	
	public Solution<Position> solveMaze(Maze3d maze) {
		if(!cachedMazes.containsKey(maze)) {	
			try {
				//In case the algorithm is BFS.
				if(properties.getSolutionAlgorithm().toLowerCase().equals("bfs")) { 
					//Create a BFS object.
					BFS<Position> bfs = new BFS<Position>();
					//Wrap the maze with object adapter.
					Maze3dSearch search = new Maze3dSearch(maze);
					//Solve the maze.
					Solution<Position> solutionByBFS = bfs.search(search);
					//Add solution to the HashMap and send relevant message.
					cachedMazes.put(maze, solutionByBFS);
					return solutionByBFS;
				}
				//In case the algorithm is AStar.
				else if(properties.getSolutionAlgorithm().toLowerCase().equals("manhattan")) { 
					//Create a manhattanDistance object.
					ManhattanDistance manhattan = new ManhattanDistance();
					//Wrap the maze with object adapter.
					Maze3dSearch search = new Maze3dSearch(maze);
					//Create a AStar object.
					AStar<Position> aStar = new AStar<>(manhattan);
					//Solve the maze.
					Solution<Position> solutionByManhattan = aStar.search(search);
					//Add solution to the HashMap and send relevant message.
					cachedMazes.put(maze, solutionByManhattan);
					return solutionByManhattan;
				}
				else if(properties.getSolutionAlgorithm().toLowerCase().equals("air")) { 
					//Create a AirDistance object.
					AirDistance air = new AirDistance();
					//Wrap the maze with object adapter.
					Maze3dSearch search = new Maze3dSearch(maze);
					//Create a AStar object.
					AStar<Position> aStar = new AStar<>(air);
					//Solve the maze.
					Solution<Position> solutionByAir = aStar.search(search);
					//Add solution to the HashMap and send relevant message.
					cachedMazes.put(maze, solutionByAir);
					return solutionByAir;
				}
				//Any other case.
				else { 
					return null;
				}
			} catch(NullPointerException e) { 
				//In case an exception throws - send relevant message.
				return null;
			}
		}
		else {
			return cachedMazes.get(maze);
		}
	}
	@Override
	public void startSever() {
		if(!server.isOn())
			server.startServer(properties.getNumOfClients());
		this.setChanged();
		this.notifyObservers("Server Started.");
	}
	public void stopServer() {
		if (server.isOn())
			server.stopServer();
	}
	@Override
	public void exit() {
		stopServer();
	}
}
