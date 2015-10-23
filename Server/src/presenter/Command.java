package presenter;

/**
 * The Interface Command represent a commands available to perform by the model.
 */
public interface Command {
	
	/**
	 * Do a command.
	 * @param obj the parameters of the command.
	 */
	public void doCommand(Object obj);
}
