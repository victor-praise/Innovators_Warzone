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

    // general behavior

    /**
     *
     */
    abstract public void loadMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void showMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void validateMap(String[] p_baseParams, Functionality[] p_functionalities);

    // edit map state behavior

    /**
     *
     */
    abstract public void editContinent(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void editCountry(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void editMap(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void saveMap(String[] p_baseParams, Functionality[] p_functionalities);

    // game setup state behavior

    /**
     *
     */
    abstract public void gamePlayers(String[] p_baseParams, Functionality[] p_functionalities);

    /**
     *
     */
    abstract public void assignCountries(String[] p_baseParams, Functionality[] p_functionalities);

    // reinforcement state behavior

    /**
     *
     */
    abstract public void reinforce();

    // attack state behavior

    /**
     *
     */
    abstract public void attack();

    // fortify state behavior

    /**
     *
     */
    abstract public void fortify();

    // end state behavior

    /**
     *
     */
    abstract public void endGame();

    // go to next phase

    /**
     *
     */
    abstract public void next();

    /**
     *  Commit the current state and move to next
     */
    // methods common to all states
    abstract public void commit();

    /**
     * quit the game
     */
    abstract public void quit();

    /**
     *
     */
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
    }

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
