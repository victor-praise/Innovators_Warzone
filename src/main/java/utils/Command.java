package main.java.utils;

/**
 * Model class representable for a command given by the user
 * @author kevin on 2023-09-21
 * @version 1.0
 */
public class Command {

    /**
     * base of the user input command
     */
    public BaseCommand command;

    /**
     * parameters which immediately follow the command, e.g. filename for loadmap command
     */
    public String[] baseParams;

    /**
     * a list of functionalities extracted from user input
     * @see main.java.utils.Functionality
     */
    public Functionality[] functionalities;

    /**
     * Creates a new Command object with given baseCommand and/or any params and/or any functionalities
     * @param baseCommand represents actual command input by the user, e.g. showmap
     * @param baseParams represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public Command(BaseCommand baseCommand, String[] baseParams, Functionality[] functionalities) {
        this.command = baseCommand;
        this.baseParams = baseParams;
        this.functionalities = functionalities;
    }

    public void execute() {
        System.out.println("Will execute command: " + this.command);
    }
}
