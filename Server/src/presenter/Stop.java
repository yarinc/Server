package presenter;

import model.MyModel;
import view.MyView;

public class Stop extends AbstractCommand implements Command {

	public Stop(MyView ui, MyModel model) {
		super(ui, model);
	}

	@Override
	public void doCommand(Object obj) {
		model.stopServer();
	}

}
