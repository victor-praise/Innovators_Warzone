package main.java.phases;

import main.java.arena.Game;
import main.java.models.Tournament;
import main.java.commands.Functionality;
import main.java.strategy.Strategy;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-10-31
 * @author victor
 */
public class Preload extends Edit {

    @Override
    public void loadTournament(String[] p_baseParams, Functionality[] p_functionalities) {
        Tournament tournament = new Tournament();
        for (Functionality functionality: p_functionalities) {
            setupTournament(functionality, tournament);
        }
        setValidCommand(true);
        Game.sharedInstance().setTournament(tournament);
    }

    public Preload() {
        LogEntryBuffer.getInstance().log("==== Preload phase ====" + "\n");
        displayValidCommands();
    }
    /**
     * Loads a valid map if present in correct phase, otherwise displays invalid command message
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        super.loadMap(p_baseParams, p_functionalities);
        if (!Game.sharedInstance().isMapEmpty()) {
            System.out.println(" --- SINGLE-MODE GAME BEGINS ---");
            setValidCommand(true);
            Game.sharedInstance().setD_gamePhase(new PostLoad());
        }
    }

    @Override
    public void editMap(String[] p_baseParams, Functionality[] p_functionalities) {
        super.editMap(p_baseParams, p_functionalities);
        LogEntryBuffer.getInstance().log("\n" + "[PreLoad]: Editing new map, changing state to post load");
        setValidCommand(true);
        Game.sharedInstance().setD_gamePhase(new PostLoad());
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
     * Moves to next state based on state diagram
     */
    public void next() {
        LogEntryBuffer.getInstance().log("[PreLoad]: must load map, currently in pre load phase");
    }

    /**
     * Displays invalid command message and prints the allowed commands
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
        LogEntryBuffer.getInstance().log("1. loadmap [filename]");
        LogEntryBuffer.getInstance().log("2. tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
        LogEntryBuffer.getInstance().log("quit");
        LogEntryBuffer.getInstance().log(" --- ");
    }
    private void setupTournament(Functionality functionality, Tournament tournament) {
        switch (functionality.functionality) {
            case MaxTurns:
                try {
                    int turns = Integer.parseInt(functionality.functionalityParams[0]);
                    tournament.setMaxNumberOfTurns(turns);
                } catch (NumberFormatException exception) {
                    tournament.setMaxNumberOfTurns(0);
                }
                break;

            case NumberOfGame:
                try {
                    int numberOfGames = Integer.parseInt(functionality.functionalityParams[0]);
                    tournament.setNumberOfGames(numberOfGames);
                } catch (NumberFormatException exception) {
                    tournament.setNumberOfGames(0);
                }
                break;

            case PlayerStrategies:
                Strategy[] playerStrategies = new Strategy[functionality.functionalityParams.length];
                int index = 0;
                for (String strategyString: functionality.functionalityParams) {
                    playerStrategies[index++] = Strategy.from(strategyString);
                }
                tournament.setPlayerStrategies(playerStrategies);

            case MapFiles:
                tournament.setMapNames(functionality.functionalityParams);
                break;

            default:
                System.out.println("Functionality " + functionality.toString() + " undetermined for Tournament command");
                break;
        }
    }}
