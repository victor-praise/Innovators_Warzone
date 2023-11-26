package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.models.Player;
import main.java.strategy.Strategy;
import main.java.strategy.StrategyFactory;
import main.java.utils.Constants;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Scanner;

/**
 * @author kevin on 2023-11-02
 */
public class PlaySetup extends Play {

    /**
     * Reads input from Console
     */
    Scanner reader = new Scanner(System.in);

    public PlaySetup() {
        LogEntryBuffer.getInstance().log("==== Creating Players for Single-Game Mode ====" + "\n");
//        displayValidCommands();
        createPlayers();
        assignCountries(null, null);
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
        if (Game.sharedInstance().getD_players().size() > 1) {
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
     * End game execution
     */
    @Override
    public void endGame() {
        Game.sharedInstance().setD_gamePhase(new End());
    }

    /**
     * Displays invalid command message and prints the allowed commands in this Phase
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        displayValidCommands();
    }

    /**
     * Display All Valid Commands in this State
     */
    private void displayValidCommands() {
        LogEntryBuffer.getInstance().log("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        LogEntryBuffer.getInstance().log("1. gameplayer [-add playername] [-remove playername]");
        LogEntryBuffer.getInstance().log("2. assigncountries");
        LogEntryBuffer.getInstance().log("quit");
        LogEntryBuffer.getInstance().log(" --- ");
    }

    public void createPlayers() {
        int playersCount = readNumberOfPlayers();
        Strategy[] playerStrategies = readStrategiesForEachPlayer(playersCount);
        String playerNamePrefix = "Player";
        for (int playerIndex = 0; playerIndex < playersCount; playerIndex++) {
            String l_name = playerNamePrefix + (playerIndex + 1);
            Strategy strategy = playerStrategies[playerIndex];
            Player nextPlayer = new Player(l_name, StrategyFactory.generateStrategyObject(strategy));
            System.out.println("Created new player with name: " + l_name + " and strategy: " + strategy.toString());
            Game.sharedInstance().addPlayer(nextPlayer);
        }
    }

    /**
     * Asks user to input number of players in console
     * @return number of players
     */
    private int readNumberOfPlayers() {
        int numberOfPlayers = 0;
        boolean isInvalid = true;
        int maxPlayers = Constants.MAX_PLAYERS_ALLOWED;
        do {
            System.out.println("Please enter the number of players between 1 and " + maxPlayers + ": ");
            String input = reader.nextLine();
            try {
                int received = Integer.parseInt(input);
                if (received > 0 && received < 10) {
                    numberOfPlayers = received;
                    isInvalid = false;
                } else {
                    System.out.println("Invalid. Please enter a number between 1 and 10" + maxPlayers);
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid. Please enter a number between 1 and " + maxPlayers);
            }
        } while (isInvalid);
        return numberOfPlayers;
    }

    private Strategy[] readStrategiesForEachPlayer(int size) {
        Strategy[] strategies = new Strategy[size];
        int nextIndex = 0;
        while (nextIndex < size) {
            System.out.println("Select Strategy for player " + (nextIndex + 1));
            strategies[nextIndex++] = readUntillValidStrategy();
        }
        return strategies;
    }

    private Strategy readUntillValidStrategy() {
        boolean isInvalid = true;
        Strategy strategySelected = null;
        Strategy[] allValues = Strategy.values();
        do {
            displayAvailableStrategies();
            String input = reader.nextLine();
            try {
                int received = Integer.parseInt(input);
                if (received >= 1 && received <= allValues.length) {
                    strategySelected = allValues[received - 1];
                    isInvalid = false;
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid choice.");
            }
        } while (isInvalid);
        return strategySelected;
    }

    private void displayAvailableStrategies() {
        System.out.println("Available Strategies are: ");
        System.out.println("1. Human - Requires user interaction");
        System.out.println("2. Aggressive - Centralization of forces and then attack");
        System.out.println("3. Benevolent - Never attacks, reinforce its weaker country");
        System.out.println("4. Random - Random decision on attacking, deploying and moving");
        System.out.println("5. Cheater - Conquer all the neighbour and double on borders with enemies");
        System.out.println("Please enter a value between 1 and 5 ... ");
    }
}
