package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.Maze3dSearch;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.AirDistance;
import algorithms.search.BFS;
import algorithms.search.ManhattanDistance;
import algorithms.search.Solution;
import presenter.MazeToSolution;
import presenter.Properties;

public class MyServerModel extends Observable implements MyModel {
	
	private HashMap<Maze3d,Solution<Position>> cachedMazes;
	private Properties properties;
	
	@SuppressWarnings("unchecked")
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
		//Try to load the cached mazes file
		try {
				FileInputStream fis=new FileInputStream(properties.getCacheMazes());
				cachedMazes = new HashMap<Maze3d,Solution<Position>>();
				GZIPInputStream gzis=new GZIPInputStream(fis);
				ObjectInputStream in=new ObjectInputStream(gzis);
				cachedMazes=(HashMap<Maze3d,Solution<Position>>)in.readObject();
				in.close();
		} catch (IOException | ClassNotFoundException e) { 
			cachedMazes = new HashMap<Maze3d,Solution<Position>> ();
		}
	}
	
	public void solveMaze(Maze3d maze) {
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
					MazeToSolution answer = new MazeToSolution(maze,solutionByBFS);
					this.zipAndSave();
					this.setChanged();
					this.notifyObservers(answer);
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
					MazeToSolution answer = new MazeToSolution(maze,solutionByManhattan);
					this.zipAndSave();
					this.setChanged();
					this.notifyObservers(answer);
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
					MazeToSolution answer = new MazeToSolution(maze,solutionByAir);
					this.zipAndSave();
					this.setChanged();
					this.notifyObservers(answer);
				}
				//Any other case.
				else { 
					this.setChanged();
					this.notifyObservers("Algorithm not found.");
				}
			} catch(NullPointerException e) { 
				//In case an exception throws - send relevant message.
				this.setChanged();
				this.notifyObservers("Invalid maze name.");
			}
		}
		else {
			MazeToSolution answer = new MazeToSolution(maze,cachedMazes.get(maze));
			this.setChanged();
			this.notifyObservers(answer);
		}
	}
	
	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public void stopServer() {
		this.setChanged();
		this.notifyObservers("stop");
	}

	@Override
	public void startSever() {
		
	}

	@Override
	public void exit() {
		
	}
	private void zipAndSave(){
		//Try to zip and save the cached mazes file to the disk.
		try {
			FileOutputStream fos=new FileOutputStream(properties.getCacheMazes());
		    GZIPOutputStream gzos=new GZIPOutputStream(fos);
		    ObjectOutputStream out=new ObjectOutputStream(gzos);
		    out.writeObject(cachedMazes);
		    out.flush();
		    out.close();
		} catch (IOException e) {
			this.setChanged();
			this.notifyObservers("Error caching solutions");
		  }
	}
}
