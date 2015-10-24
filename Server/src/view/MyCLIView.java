package view;

import java.util.Observable;

/**
 * The Class MyView communicate with the user.
 */
public class MyCLIView extends Observable implements MyView {

	private CLI cli;
	
	/**
	 * Instantiates a new MyView object.
	 * @param cli the CLI object
	 */
	public MyCLIView(CLI cli) { 
		this.cli = cli;
	}
	
	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		new Thread(cli).start();
	}

	/* (non-Javadoc)
	 * @see view.View#printString(java.lang.String)
	 */
	@Override
	public void printString(String string) {
		cli.getOut().println(string);
		cli.getOut().flush();
	}

	/* (non-Javadoc)
	 * @see view.View#inputToPresenter(java.lang.String)
	 */
	public void inputToPresenter(String line) {
		this.setChanged();
		this.notifyObservers(line);
	}

}
