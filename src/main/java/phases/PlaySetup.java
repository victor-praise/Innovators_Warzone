package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;

/**
 * @author kevin on 2023-11-02
 */
public class PlaySetup extends Play {

    /**
     * Loads a valid map if present in correct phase, otherwise displays invalid command message
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Display the current list of continents countries and neighbours
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void showMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Validates the current map
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void validateMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Allows user to 'Add' or 'Remove' continents
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void editContinent(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Allows user to 'Add' or 'Remove' countries
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void editCountry(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Allows user to 'Add' or 'Remove' neighbours
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Load a map from an existing “domination” map file, or create a new map from scratch if the file does not exist
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void editMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Save a map to a text file exactly as edited (using the “domination” game map format)
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void saveMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * Allows user to 'Add' or 'Remove' players
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void gamePlayers(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            System.out.println("[GamePlayer]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];
        String l_playerName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[GamePlayer]: Adding a player requires Player Name");
                    System.out.println("[GamePlayer]: Adding a player failed");
                    return;
                }
                l_playerName = l_function.functionalityParams[0];
                if (Game.sharedInstance().addPlayer(l_playerName)) {
                    System.out.println("[GamePlayer]: Adding a player successfull");
                } else {
                    System.out.println("[GamePlayer]: Adding a player failed");
                }
                break;
            case Remove:
                l_playerName = l_function.functionalityParams[0];
                if (Game.sharedInstance().removePlayer(l_playerName)) {
                    System.out.println("[GamePlayer]: Removing a player successfull");
                } else {
                    System.out.println("[GamePlayer]: Removing a player failed");
                }
                break;
            default:
                System.out.println("[EditContinent]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

    /**
     * Randomly assigns all the countries to different players
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void assignCountries(String[] p_baseParams, Functionality[] p_functionalities) {
        Game.sharedInstance().assignCountriesToPlayers();
        if (!Game.sharedInstance().getD_players().isEmpty()) {
            Game.sharedInstance().setD_gamePhase(new Reinforcement());
            System.out.println("--- Moving to Reinforcement phase --- ");

            // change state when all countries assigned
            next();
        }
    }

    /**
     * At the beginning of every turn a Player is given a number of reinforcement armies, calculated according to the Warzone rules.
     */
    @Override
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * Requests each player to issue commands in a round-robin fashion
     */
    @Override
    public void attack() {
        printInvalidCommandMessage();
    }

    /**
     * GameEngine asks each Player for their next order and then executes them
     */
    @Override
    public void fortify() {
        printInvalidCommandMessage();
    }

    /**
     * Move to the next phase based on the State Pattern designed for this game
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Reinforcement());
    }

    /**
     * Displays invalid command message and prints the allowed commands in this Phase
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        System.out.println("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        System.out.println("1. gameplayer [-add playername] [-remove playername]");
        System.out.println("2. assigncountries");
        System.out.println(" --- ");
    }
}
