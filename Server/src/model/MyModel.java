package model;

import algorithms.mazeGenerators.Maze3d;
import presenter.Properties;

/**
 * The Interface MyModel is a interface for all models classes for the MVP architecture.
 */
public interface MyModel {
	
	/**
	 * Gets the properties file class.
	 * @return the properties
	 */
	public Properties getProperties();
	
	/**
	 * Solve the given maze.
	 * @param maze the maze
	 */
	public void solveMaze(Maze3d maze);
	
	/**
	 * Stops the server.
	 */
	public void stopServer();
	
	/**
	 * Starts the server.
	 */
	public void startServer();
	
	/**
	 * Exits from the server.
	 */
	public void exit();

}
