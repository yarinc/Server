package presenter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * The Class MazeToSolution hold in one class a maze3d and it's solution.
 */
public class MazeToSolution {

	private Maze3d maze;
	private Solution<Position> solution;
	
	/**
	 * Instantiates a new MazeToSolution object.
	 * @param maze the maze
	 * @param solution the solution
	 */
	public MazeToSolution(Maze3d maze, Solution<Position> solution){
		this.maze = maze;
		this.solution = solution;
	}

	/**
	 * Gets the maze.
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Gets the solution.
	 * @return the solution
	 */
	public Solution<Position> getSolution() {
		return solution;
	}

}
