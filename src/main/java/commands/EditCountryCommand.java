package main.java.commands;

import main.java.arena.Game;

/**
 * Commands pertaining to modifying countries list
 * @author kevin on 2023-09-27
 */
public class EditCountryCommand extends Command {

    /**
     * Creates a new EditCountry command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public EditCountryCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.EditCountry, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (functionalities == null || functionalities.length == 0) {
            System.out.println("[EditCountry]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = functionalities[0];
        String l_countryName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditCountry]: Adding a country requires 2 parameters\n[1] Country Name\n[2] Continent Name");
                    System.out.println("[EditCountry]: Adding a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                String l_continentName = l_function.functionalityParams[1];
                Game.sharedInstance().getD_map().addCountry(l_countryName, l_continentName);
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[EditCountry]: Removing a country requires 1 parameters\n[1] Country Name");
                    System.out.println("[EditCountry]: Removing a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeCountry(l_countryName)) {
                    System.out.println("[EditCountry]: Removed country with name: " + l_countryName);
                } else {
                    System.out.println("[EditCountry]: Removing a country failed");
                }
                break;

            default:
                System.out.println("[EditCountry]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

}
