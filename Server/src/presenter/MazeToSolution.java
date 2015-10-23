package presenter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeToSolution {
	private Maze3d maze;
	private Solution<Position> solution;
	
	public MazeToSolution(Maze3d maze, Solution<Position> solution){
		this.maze = maze;
		this.solution = solution;
	}

	public Maze3d getMaze() {
		return maze;
	}

	public Solution<Position> getSolution() {
		return solution;
	}

}
