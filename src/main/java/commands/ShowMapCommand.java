package main.java.commands;

import main.java.arena.Game;

/**
 * @author Sadiqali
 * ShowMapCommand base method for Command Superclass to execute showMap() method
 */

public class ShowMapCommand extends Command {
    /**
     * Creates a new EditContinent command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public ShowMapCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.ShowMap, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        Game.sharedInstance().getD_map().show();
    }
}
