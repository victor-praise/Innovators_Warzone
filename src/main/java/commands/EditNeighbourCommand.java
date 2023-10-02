package main.java.commands;

import main.java.arena.Game;

/**
 * Command to either add or remove a country from all of its neighbours list
 * @author kevin on 2023-09-27
 */
public class EditNeighbourCommand extends Command {

    /**
     * Creates a new EditNeighbour command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public EditNeighbourCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.EditNeighbour, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (functionalities == null || functionalities.length == 0) {
            System.out.println("[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = functionalities[0];
        String l_countryName = null;
        String l_neighbourName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditNeighbour]: Adding a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    System.out.println("[EditNeighbour]: Adding a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().addNeighbour(l_countryName, l_neighbourName)) {
                    System.out.println("[EditNeighbour]: Added " + l_countryName + " as a neighbour to: " + l_neighbourName);
                } else {
                    System.out.println("[EditNeighbour]: Adding a neighbour failed");
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditNeighbour]: Removing a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    System.out.println("[EditNeighbour]: Removing a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().removeNeighbour(l_countryName, l_neighbourName)) {
                    System.out.println("[EditNeighbour]: Removed " + l_countryName + " and : " + l_neighbourName + "as neighbours");
                } else {
                    System.out.println("[EditNeighbour]: Removing a neighbour failed");
                }
                break;

            default:
                System.out.println("[EditNeighbour]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }

    }
}