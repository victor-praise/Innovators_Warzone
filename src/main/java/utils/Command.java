package main.java.utils;

/**
 * @author kevin on 2023-09-21
 */
public class Command {
    BaseCommand command;
    String[] baseParams;
    Functionality[] functionalities;

    public Command(BaseCommand baseCommand, String[] baseParams, Functionality[] functionalities) {
        this.command = baseCommand;
        this.baseParams = baseParams;
        this.functionalities = functionalities;
    }
}
