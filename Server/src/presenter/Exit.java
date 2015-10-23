package presenter;

import model.MyModel;
import view.MyView;

public class Exit extends AbstractCommand implements Command {

	public Exit(MyView ui, MyModel model) {
		super(ui, model);
	}

	@Override
	public void doCommand(Object obj) {
		model.exit();
	}

}
