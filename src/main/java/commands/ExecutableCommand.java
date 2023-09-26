package main.java.commands;

/**
 * @author kevin on 2023-09-24
 */
public interface ExecutableCommand {
    /**
     * For any given command by user, perform sanity and then proceed to implement the required feature
     */
    public void execute();
}
