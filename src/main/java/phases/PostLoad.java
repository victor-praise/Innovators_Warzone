package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.exceptions.MapInvalidException;
import main.java.services.MapService;
import main.java.utils.logger.LogEntryBuffer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author kevin on 2023-10-31
 * @author victor
 */
public class PostLoad extends Edit {

    public PostLoad() {
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
    }

    /**
     * Allows user to 'Add' or 'Remove' countries
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    public void editCountry(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            LogEntryBuffer.getInstance().log("[EditCountry]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];
        String l_countryName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    LogEntryBuffer.getInstance().log("[EditCountry]: Adding a country requires 2 parameters\n[1] Country Name\n[2] Continent Name");
                    LogEntryBuffer.getInstance().log("[EditCountry]: Adding a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                String l_continentName = l_function.functionalityParams[1];
                Game.sharedInstance().getD_map().addCountry(l_countryName, l_continentName);
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    LogEntryBuffer.getInstance().log("[EditCountry]: Removing a country requires 1 parameters\n[1] Country Name");
                    LogEntryBuffer.getInstance().log("[EditCountry]: Removing a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeCountry(l_countryName)) {
                    LogEntryBuffer.getInstance().log("[EditCountry]: Removed country with name: " + l_countryName);
                } else {
                    LogEntryBuffer.getInstance().log("[EditCountry]: Removing a country failed");
                }
                break;

            default:
                LogEntryBuffer.getInstance().log("[EditCountry]: Functionality undefined. Expectation:  '-add' or '-remove'");
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
        String l_message;
        if (p_functionalities == null || p_functionalities.length == 0) {
            l_message = "[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'";
            LogEntryBuffer.getInstance().log(l_message);
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }
        Functionality l_function = p_functionalities[0];

        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {

                    LogEntryBuffer.getInstance().log("[EditContinent]: Adding a continent requires 2 parameters\n[1] Continent Name\n[2] Bonus Points");
                    LogEntryBuffer.getInstance().log("[EditContinent]: Adding a continent failed");
                    LogEntryBuffer.getInstance().log("[EditContinent]: Adding a continent failed");
                    return;
                }
                try {
                    String l_continentName = l_function.functionalityParams[0];
                    int l_bonusValue = Integer.parseInt(l_function.functionalityParams[1]);
                    Game.sharedInstance().getD_map().addContinent(l_continentName, l_bonusValue);
                    l_message = "Continent " + l_continentName + " with bonus value " + l_bonusValue + " has been successfully added";
                    LogEntryBuffer.getInstance().log(l_message);
                } catch (NumberFormatException nfe) {
                    l_message = "[EditContinent]: Invalid format: bonus points must be an integer, found: [ " + l_function.functionalityParams[1] + " ]";
                    LogEntryBuffer.getInstance().log(l_message);
                    LogEntryBuffer.getInstance().log(l_message);
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    LogEntryBuffer.getInstance().log("[EditContinent]: Removing a continent requires 1 parameters\n[1] Continent Name");
                    LogEntryBuffer.getInstance().log("[EditContinent]: Removing a continent failed");
                    LogEntryBuffer.getInstance().log("[EditContinent]: Removing a continent failed");
                    return;
                }
                String l_continentName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeContinent(l_continentName)) {
                    l_message = "[EditContinent]: Removed continent with name: " + l_continentName;
                    LogEntryBuffer.getInstance().log(l_message);
                    LogEntryBuffer.getInstance().log(l_message);
                } else {
                    l_message = "[EditContinent]: Removing a continent failed";
                    LogEntryBuffer.getInstance().log(l_message);
                    LogEntryBuffer.getInstance().log(l_message);
                }
                break;

            default:
                LogEntryBuffer.getInstance().log("[EditContinent]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

    /**
     * Allows user to 'Add' or 'Remove' neighbours
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    @Override
    public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            LogEntryBuffer.getInstance().log("[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];
        String l_countryName = null;
        String l_neighbourName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Adding a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Adding a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().addNeighbour(l_countryName, l_neighbourName)) {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Added " + l_countryName + " as a neighbour to: " + l_neighbourName);
                } else {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Adding a neighbour failed");
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Removing a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Removing a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().removeNeighbour(l_countryName, l_neighbourName)) {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Removed " + l_countryName + " and : " + l_neighbourName + "as neighbours");
                } else {
                    LogEntryBuffer.getInstance().log("[EditNeighbour]: Removing a neighbour failed");
                }
                break;

            default:
                LogEntryBuffer.getInstance().log("[EditNeighbour]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

    /**
     * Save a map to a text file exactly as edited (using the “domination” game map format)
     *
     * @param p_baseParams      parameters for this command
     * @param p_functionalities functionalities of this command
     */
    public void saveMap(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities != null && p_functionalities.length != 0) {
            LogEntryBuffer.getInstance().log("[SaveMapCommand]: No functionality is supported for SaveMap command");
            return;
        }

        MapService mapService = new MapService();
        String l_filename;

        if (p_baseParams == null) {
            LogEntryBuffer.getInstance().log("[SaveMapCommand]: file name is mandatory ");
            return;
        } else {
            Optional<String> optionalFileName = Arrays.stream(p_baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                LogEntryBuffer.getInstance().log("[SaveMapCommand]: file name is mandatory ");
                return;
            }

            if (!l_filename.endsWith(".map")) {
                l_filename = l_filename.concat(".map");
            }
            mapService.saveMap(l_filename);
            LogEntryBuffer.getInstance().log("[SaveMap]: Map saved successfully.");

            Game.sharedInstance().setD_gamePhase(new PlaySetup());
            LogEntryBuffer.getInstance().log("[PostLoad]: --- Moving to PlaySetup ---");
        } catch (IOException error) {
            LogEntryBuffer.getInstance().log("[SaveMapCommand]: Error while saving map to: " + l_filename + " " + error.getLocalizedMessage());
            throw new RuntimeException(error);
        } catch (MapInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to next state based on state diagram
     */
    public void next() {
        LogEntryBuffer.getInstance().log("must load map");
    }

    /**
     * Displays invalid command message and prints the allowed commands
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
    }

    /**
     * Display All Valid Commands in this State
     */
    private void displayValidCommands() {
        LogEntryBuffer.getInstance().log("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        LogEntryBuffer.getInstance().log("1. editcontinent [-add continentID continentvalue] [-remove continentID]");
        LogEntryBuffer.getInstance().log("2. editcountry [-add countryID continentID] [-remove countryID]");
        LogEntryBuffer.getInstance().log("3. editneighbor [-add countryID neighborcountryID] [-remove countryID]");
        LogEntryBuffer.getInstance().log("4. showmap");
        LogEntryBuffer.getInstance().log("5. savemap [filename]");
        LogEntryBuffer.getInstance().log("6. validatemap");
        LogEntryBuffer.getInstance().log(" --- ");
    }
}
