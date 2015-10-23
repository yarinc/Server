package model;

import algorithms.mazeGenerators.Maze3d;
import presenter.Properties;

public interface MyModel {
	
	public Properties getProperties();
	public void solveMaze(Maze3d maze);
	public void stopServer();
	public void startSever();
	public void exit();

}
