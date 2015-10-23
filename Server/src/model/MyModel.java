package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface MyModel {
	
	public Solution<Position> solveMaze(Maze3d maze);
	public void startSever();
	public void stopServer();
	public void exit();

}
