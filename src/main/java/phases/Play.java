package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;

/**
 * @author kevin on 2023-11-02
 */
public abstract class Play extends Phase {
    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void showMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void validateMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void editContinent(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void editCountry(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void editMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void saveMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void gamePlayers(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void assignCountries(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    @Override
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    @Override
    public void attack() {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    @Override
    public void fortify() {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    @Override
    public void endGame() {
        Game.endGamePlay();
    }

    /**
     * Commit the current state and move to next
     */
    @Override
    public void commit() {
        next();
    }

    /**
     * quit the game
     */
    @Override
    public void quit() {
        endGame();
    }
}
