package main.java.commands;

import main.java.arena.Game;

/**
 * Commands pertaining to modifying continents
 * @author kevin on 2023-09-24
 */
public class EditContinentCommand extends Command {

    /**
     * Creates a new EditContinent command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public EditContinentCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.EditContinent, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (d_functionalities == null || d_functionalities.length == 0) {
            System.out.println("[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = d_functionalities[0];

        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditContinent]: Adding a continent requires 2 parameters\n[1] Continent Name\n[2] Bonus Points");
                    System.out.println("[EditContinent]: Adding a continent failed");
                    return;
                }
                try {
                    String l_continentName = l_function.functionalityParams[0];
                    int l_bonusValue = Integer.parseInt(l_function.functionalityParams[1]);
                    Game.sharedInstance().getD_map().addContinent(l_continentName, l_bonusValue);
                } catch (NumberFormatException nfe) {
                    System.out.println("[EditContinent]: Invalid format: bonus points must be an integer, found: [ " + l_function.functionalityParams[1] +" ]");
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[EditContinent]: Removing a continent requires 1 parameters\n[1] Continent Name");
                    System.out.println("[EditContinent]: Removing a continent failed");
                    return;
                }
                String l_continentName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeContinent(l_continentName)) {
                    System.out.println("[EditContinent]: Removed continent with name: " + l_continentName);
                } else {
                    System.out.println("[EditContinent]: Removing a continent failed");
                }
                break;

            default:
                System.out.println("[EditContinent]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }
}
