package main.java.commands;

/**
 * All executable commands can be called to execute() themselves
 * @author kevin on 2023-09-24
 */
public interface ExecutableCommand {
    /**
     * For any given command by user, perform sanity and then proceed to implement the required feature
     */
    public void execute();
}
