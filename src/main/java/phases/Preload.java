package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.models.Tournament;
import main.java.strategy.Strategy;
import main.java.utils.logger.LogEntryBuffer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author kevin on 2023-10-31
 * @author victor
 */
public class Preload extends Edit {

    @Override
    public void loadTournament(String[] p_baseParams, Functionality[] p_functionalities) {
        Tournament tournament = new Tournament();
        boolean validTournament = true;
        for (Functionality functionality: p_functionalities) {
           if(!setupTournament(functionality, tournament)){
               validTournament = false;
           }
        }


        if(validTournament){
            setValidCommand(true);
            Game.sharedInstance().setTournament(tournament);
        }
        else{
           printInvalidCommandMessage();
        }
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
    private boolean setupTournament(Functionality functionality, Tournament tournament) {
        boolean isValid = false;
        switch (functionality.functionality) {

            case MaxTurns:
                try {

                    int turns = Integer.parseInt(functionality.functionalityParams[0]);
                    if(turns >= 10 && turns <= 50){
                        isValid = true;
                        tournament.setMaxNumberOfTurns(turns);
                    }
                    else{
                        LogEntryBuffer.getInstance().log("[Tournament: ] Invalid number of turns must be between 10 and 50");
                        isValid = false;
                    }

                } catch (NumberFormatException exception) {
                    tournament.setMaxNumberOfTurns(0);
                }
                break;

            case NumberOfGame:
                try {

                    int numberOfGames = Integer.parseInt(functionality.functionalityParams[0]);
                    if(numberOfGames >= 1 && numberOfGames <= 5){
                        tournament.setNumberOfGames(numberOfGames);
                        isValid = true;
                    }
                    else{
                        LogEntryBuffer.getInstance().log("[Tournament: ] Invalid number of games must be between 1 and 5");
                        isValid = false;
                    }

                } catch (NumberFormatException exception) {
                    tournament.setNumberOfGames(0);
                }
                break;

            case PlayerStrategies:
                Strategy[] playerStrategies = new Strategy[functionality.functionalityParams.length];
                List<String> l_validStrategies = Arrays.asList("Aggressive", "Random", "Benevolent", "Cheater");
                int index = 0;
                if(functionality.functionalityParams.length < 2 || functionality.functionalityParams.length > 4){
                    isValid = false;
                    LogEntryBuffer.getInstance().log("{Tournament: Number of strategies must be between 2 and 4");
                    break;
                }
                for (String strategyString: functionality.functionalityParams) {
                    if(!l_validStrategies.contains(strategyString)){
                        isValid = false;
                    }
                    else{
                        isValid = true;
                    }
                    playerStrategies[index++] = Strategy.from(strategyString);
                }

                if(isValid){
                    tournament.setPlayerStrategies(playerStrategies);
                }
                else{
                    LogEntryBuffer.getInstance().log("[Tournament: ] Invalid Strategy Only Aggressive, Benevolent, Random, Cheater strategies are valid");
                }

                break;

            case MapFiles:

                String resFolderPath = System.getProperty("user.dir") + File.separator + "res";
                for(String mapName: functionality.functionalityParams){
                    String filePath = resFolderPath + File.separator + mapName;
                    File file = new File(filePath);
                    if (file.exists() && file.isFile()) {
                        isValid = true;
                    }
                    else{
                        isValid = false;
                    }
                }
                if(isValid){
                    tournament.setMapNames(functionality.functionalityParams);
                }
                else{
                    LogEntryBuffer.getInstance().log("[Tournament: ] Invalid map. Please ensure map names are valid");
                }

                break;

            default:
                System.out.println("Functionality " + functionality.toString() + " undetermined for Tournament command");
                break;
        }
        return isValid;
    }}
