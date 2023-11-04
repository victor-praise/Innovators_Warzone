package main.java.phases;

import main.java.commands.BaseCommand;
import main.java.commands.Command;
import main.java.commands.Functionality;

/**
 * This is an abstract class that defines all the functionalities of the game.
 * Each functionality is applicable with only a specific concrete Phase class implementation
 * @author kevin on 2023-10-30
 */
public abstract class Phase {

    /**
     * Loads a valid map if present in correct phase, otherwise displays invalid command message
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void loadMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Display the current list of continents countries and neighbours
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void showMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Validates the current map
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void validateMap(String[] p_baseParams, Functionality[] p_functionalities);

    // edit map state behavior

    /**
     * Allows user to 'Add' or 'Remove' continents
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void editContinent(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Allows user to 'Add' or 'Remove' countries
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void editCountry(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Allows user to 'Add' or 'Remove' neighbours
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Load a map from an existing “domination” map file, or create a new map from scratch if the file does not exist
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void editMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Save a map to a text file exactly as edited (using the “domination” game map format)
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void saveMap(String[] p_baseParams, Functionality[] p_functionalities);

    // game setup state behavior

    /**
     * Allows user to 'Add' or 'Remove' players
     *
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void gamePlayers(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     * Randomly assigns all the countries to different players
     * @param p_baseParams parameters for this command
     * @param p_functionalities functionalities of this command
     */
    abstract public void assignCountries(String[] p_baseParams, Functionality[] p_functionalities);

    // reinforcement state behavior

    /**
     * At the beginning of every turn a Player is given a number of reinforcement armies, calculated according to the Warzone rules.
     */
    abstract public void reinforce();

    // attack state behavior

    /**
     * Requests each player to issue commands in a round-robin fashion
     */
    abstract public void attack();

    // fortify state behavior

    /**
     * GameEngine asks each Player for their next order and then executes them
     */
    abstract public void fortify();

    // end state behavior

    /**
     * Terminates the game
     */
    abstract public void endGame();

    // go to next phase

    /**
     * Moves to next state based on state diagram
     */
    abstract public void next();

    /**
     * quit the game
     */
    abstract public void quit();

    /**
     * Displays invalid command message and prints the allowed commands
     */
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
    }

    /**
     * Executes the specified command
     *
     * @param p_command the command to execute
     */
    public void executeCommand(Command p_command) {
        if (p_command.d_command == BaseCommand.None) {
            return;
        } else {
            switch (p_command.d_command) {
                case EditContinent:
                    editContinent(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case EditCountry:
                    editCountry(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case EditNeighbour:
                    editNeighbour(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case EditMap:
                    editMap(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case ShowMap:
                    showMap(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case SaveMap:
                    saveMap(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case ValidateMap:
                    validateMap(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case GamePlayer:
                    gamePlayers(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case LoadMap:
                    loadMap(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case AssignCountries:
                    assignCountries(p_command.d_baseParams, p_command.d_functionalities);
                    break;

                case Quit:
                    quit();
                    break;

                default:
                    break;
            }
        }
    }
}
