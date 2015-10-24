package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class CLI starts in a new thread a CLI for the user.
 */
public class CLI implements Runnable {
	
	private BufferedReader in; 
	private PrintWriter out;
	private MyView ui;
	
	/**
	 * Gets in.
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}
	
	/**
	 * Sets the view.
	 * @param ui the new user interface
	 */
	public void setView(MyView ui) { 
		this.ui = ui;
	}
	
	/**
	 * Gets out.
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}
	
	/**
	 * Instantiates a new CLI object.
	 * @param in the in object
	 * @param out the out object
	 */
	public CLI(BufferedReader in, PrintWriter out) { 
		this.in = in;
		this.out = out;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String line;
		try {
			//Getting the input from the BufferdReader as long as it's not 'exit'.
			while (!(line = in.readLine()).equals("exit")) {
				//sending the input to the presenter.
				ui.inputToPresenter(line);
			}
			//'exit' has entered - sending it to the presenter.
			ui.inputToPresenter(line);
		} catch (IOException e) {
			//In case an exception throws - send relevant message.
			ui.printString("I/O error occurred.");
		}
	}
}
