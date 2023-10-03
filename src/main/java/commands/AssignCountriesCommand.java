package main.java.commands;

import main.java.arena.Game;

/**
 *
 * @author kevin on 2023-10-03
 */
public class AssignCountriesCommand extends Command {
    /**
     * Creates a command which will randomly assign countries to each player when executed
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public AssignCountriesCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.AssignCountries, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        // We simply pass the responsibility to game class
        Game.sharedInstance().assignCountriesToPlayers();
    }
}
