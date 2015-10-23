package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyServerModel;
import presenter.Presenter;
import view.CLI;
import view.MyCLIView;

public class Run {
	
	public static void main(String[] args) {
		MyServerModel m = new MyServerModel();
		CLI cli = new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		MyCLIView ui = new MyCLIView(cli);
		cli.setView(ui);
		Presenter p = new Presenter(m,ui);
		ui.addObserver(p);
		m.addObserver(p);
		ui.start();
	}
}
