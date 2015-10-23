package presenter;

import model.MyModel;
import view.MyView;

/**
 * The Class AbstractCommand implements the command interface.
 * Abstracting the class by declaring a model and ui data members.
 */
public abstract class AbstractCommand implements Command {

	protected MyView ui;
	protected MyModel model;
	
	/**
	 * Constructor for the AbstractCommand class.
	 * @param ui the user interface.
	 * @param model the model
	 */
	public AbstractCommand(MyView ui, MyModel model) {
		this.ui = ui;
		this.model = model;
	}
}
