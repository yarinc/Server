package presenter;

import algorithms.mazeGenerators.Maze3d;
import model.MyModel;
import view.MyView;

/**
 * The Class Solve gets a maze name and solves the maze
 */
public class Solve extends AbstractCommand implements Command {

	/**
	 * Instantiates a new Solve object.
	 * @param view the view
	 * @param model the model
	 */
	public Solve(MyView view, MyModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.Command#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(Object obj) {
		model.solveMaze((Maze3d)obj);

	}
}
