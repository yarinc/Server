package presenter;

import model.MyModel;
import view.MyView;

public class Start extends AbstractCommand implements Command {

	public Start(MyView ui, MyModel model) {
		super(ui, model);
	}

	@Override
	public void doCommand(Object obj) {
		model.startSever();
	}

}
