package presenter;

import model.MyModel;
import view.MyView;

public abstract class AbstractCommand implements Command {
	protected MyView ui;
	protected MyModel model;
	
	public AbstractCommand(MyView ui, MyModel model) {
		this.ui = ui;
		this.model = model;
	}

}
