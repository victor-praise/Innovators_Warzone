package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;

/**
 * @author kevin on 2023-11-02
 */
public class PlaySetup extends Play {
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
                if(Game.sharedInstance().addPlayer(l_playerName)) {
                    System.out.println("[GamePlayer]: Adding a player successfull");
                } else {
                    System.out.println("[GamePlayer]: Adding a player failed");
                }
                break;
            case Remove:
                l_playerName = l_function.functionalityParams[0];
                if(Game.sharedInstance().removePlayer(l_playerName)) {
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
     * @param p_baseParams
     * @param p_functionalities
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
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Reinforcement());
    }

    /**
     * Commit the current state and move to next
     */
    @Override
    public void commit() {
        next();
    }

    /**
     *
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
