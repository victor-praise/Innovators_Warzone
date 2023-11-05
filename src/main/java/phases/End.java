package main.java.phases;

import main.java.commands.Functionality;

/**
 * @author kevin on 2023-11-04
 */
public class End extends Phase {

    public End() {
        endGame();
    }

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
        printInvalidCommandMessage();
    }

    /**
     * Randomly assigns all the countries to different players
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void assignCountries(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
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

    @Override
    public void endGame() {
        System.out.println("Game terminated");
        System.exit(0);
    }

    /**
     * Move to the next phase based on the State Pattern designed for this game
     */
    @Override
    public void next() {
        endGame();
    }

    /**
     * quit the game
     */
    @Override
    public void quit() {
        endGame();
    }
}
