package main.java.commands;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;

/**
 * @author kevin on 2023-10-02
 */
public class ValidateMapCommand extends Command {
    /**
     * Creates a new EditContinent command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public ValidateMapCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.ValidateMap, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        try {
            if (Game.sharedInstance().getD_map().validate()) {
                System.out.println("[ValidateMap]: Map found to be valid");
            } else {
                System.out.println("[ValidateMap]: Map found to be invalid");
            }
        } catch (MapInvalidException error) {
            System.out.println("[ValidateMap]: Map found to be invalid: " + error.getMessage());
        }
    }

}
