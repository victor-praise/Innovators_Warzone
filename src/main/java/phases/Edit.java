package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.exceptions.MapInvalidException;
import main.java.services.MapService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author kevin on 2023-10-30
 */
public class Edit extends Phase {
    private static final int DEFAULT_REINFORCEMENT = 3;

    /**
     * Loads a valid map if present in correct phase, otherwise displays invalid command message
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities != null && p_functionalities.length != 0) {
            System.out.println("[EditPhase]: No functionality is supported for EditMap command");
            return;
        }
        MapService mapService = new MapService();
        String l_filename;

        if (p_baseParams == null) {
            l_filename = null;
        } else {
            Optional<String> optionalFileName = Arrays.stream(p_baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                System.out.println("[EditPhase]: file name is mandatory ");
                return;
            }

            if (!l_filename.endsWith(".map")) {
                l_filename = l_filename.concat(".map");
            }

            String l_filePath = new File("").getAbsolutePath();
            String l_file = l_filePath + File.separator + "res" + File.separator + l_filename;
            File l_fileToBeLoaded = new File(l_file);
            if (l_fileToBeLoaded.exists()) {
                mapService.loadMap(l_filename);
            } else {
                System.out.println("[LoadMapCommand]: file does not exist, please provide the name of a file that exists ");
            }
        } catch (Exception e) {
            System.out.println("[LoadMapCommand]: Error reading from file : " + e.getMessage());
        }
    }

    /**
     * Display the current list of continents countries and neighbours
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void showMap(String[] p_baseParams, Functionality[] p_functionalities) {
        Game.sharedInstance().getD_map().show();
    }

    /**
     * Validates the current map
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void validateMap(String[] p_baseParams, Functionality[] p_functionalities) {
        try {
            if (Game.sharedInstance().getD_map().validate()) {
                System.out.println("[ValidateMap]: Map found to be valid");
            } else {
                System.out.println("[ValidateMap]: Map found to be invalid");
            }
        } catch (MapInvalidException error) {
            System.out.println("[ValidateMap]: Map found to be invalid: " + error.getMessage());
        }
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
        if (p_functionalities != null && p_functionalities.length != 0) {
            System.out.println("[EditMapCommand]: No functionality is supported for EditMap command");
            return;
        }

        MapService mapService = new MapService();
        String l_filename;

        if (p_baseParams == null) {
            l_filename = null;
        } else {
            Optional<String> optionalFileName = Arrays.stream(p_baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                System.out.println("[EditMapCommand]: file name is mandatory ");
                return;
            }
            mapService.editMap(l_filename);
        } catch (IOException e) {
            System.out.println("[EditMapCommand]: Error executing edit map command: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Save a map to a text file exactly as edited (using the “domination” game map format)
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void saveMap(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities != null && p_functionalities.length != 0) {
            System.out.println("[SaveMapCommand]: No functionality is supported for SaveMap command");
            return;
        }

        MapService mapService = new MapService();
        String l_filename;

        if (p_baseParams == null) {
            System.out.println("[SaveMapCommand]: file name is mandatory ");
            return;
        } else {
            Optional<String> optionalFileName = Arrays.stream(p_baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                System.out.println("[SaveMapCommand]: file name is mandatory ");
                return;
            }

            if (!l_filename.endsWith(".map")) {
                l_filename = l_filename.concat(".map");
            }
            mapService.saveMap(l_filename);
            System.out.println("[SaveMap]: Map saved successfully.");
        } catch (IOException error) {
            System.out.println("[SaveMapCommand]: Error while saving map to: " + l_filename + " " + error.getLocalizedMessage());
            throw new RuntimeException(error);
        } catch (MapInvalidException e) {
            e.printStackTrace();
        }
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

    /**
     * Moves the game to 'End' phase which terminates the game
     */
    @Override
    public void endGame() {
        Game.endGamePlay();
    }

    /**
     * Move to the next phase based on the State Pattern designed for this game
     */
    @Override
    public void next() {
        System.out.println("[Warzone]: Undefined next state, please define in concrete Phase class");
    }

    /**
     * quit the game
     */
    @Override
    public void quit() {
        endGame();
    }
}
