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
     * @param p_baseParams
     * @param p_functionalities
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
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void showMap(String[] p_baseParams, Functionality[] p_functionalities) {
        Game.sharedInstance().getD_map().show();
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
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
     * @param p_baseParams
     * @param p_functionalities
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

    }

    /**
     *
     */
    @Override
    public void fortify() {

    }

    /**
     *
     */
    @Override
    public void endGame() {
        Game.endGamePlay();
    }

    /**
     *
     */
    @Override
    public void next() {
        System.out.println("[Warzone]: Undefined next state, please define in concrete Phase class");
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
