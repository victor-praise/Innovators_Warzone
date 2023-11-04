package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.exceptions.MapInvalidException;
import main.java.services.MapService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author kevin on 2023-10-31
 */
public class PostLoad extends Edit {

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        super.loadMap(p_baseParams, p_functionalities);
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    public void editCountry(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            System.out.println("[EditCountry]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];
        String l_countryName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditCountry]: Adding a country requires 2 parameters\n[1] Country Name\n[2] Continent Name");
                    System.out.println("[EditCountry]: Adding a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                String l_continentName = l_function.functionalityParams[1];
                Game.sharedInstance().getD_map().addCountry(l_countryName, l_continentName);
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[EditCountry]: Removing a country requires 1 parameters\n[1] Country Name");
                    System.out.println("[EditCountry]: Removing a country failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeCountry(l_countryName)) {
                    System.out.println("[EditCountry]: Removed country with name: " + l_countryName);
                } else {
                    System.out.println("[EditCountry]: Removing a country failed");
                }
                break;

            default:
                System.out.println("[EditCountry]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void editContinent(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            System.out.println("[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];

        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditContinent]: Adding a continent requires 2 parameters\n[1] Continent Name\n[2] Bonus Points");
                    System.out.println("[EditContinent]: Adding a continent failed");
                    return;
                }
                try {
                    String l_continentName = l_function.functionalityParams[0];
                    int l_bonusValue = Integer.parseInt(l_function.functionalityParams[1]);
                    Game.sharedInstance().getD_map().addContinent(l_continentName, l_bonusValue);
                } catch (NumberFormatException nfe) {
                    System.out.println("[EditContinent]: Invalid format: bonus points must be an integer, found: [ " + l_function.functionalityParams[1] +" ]");
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[EditContinent]: Removing a continent requires 1 parameters\n[1] Continent Name");
                    System.out.println("[EditContinent]: Removing a continent failed");
                    return;
                }
                String l_continentName = l_function.functionalityParams[0];
                if (Game.sharedInstance().getD_map().removeContinent(l_continentName)) {
                    System.out.println("[EditContinent]: Removed continent with name: " + l_continentName);
                } else {
                    System.out.println("[EditContinent]: Removing a continent failed");
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
    public void editNeighbour(String[] p_baseParams, Functionality[] p_functionalities) {
        if (p_functionalities == null || p_functionalities.length == 0) {
            System.out.println("[EditContinent]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = p_functionalities[0];
        String l_countryName = null;
        String l_neighbourName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditNeighbour]: Adding a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    System.out.println("[EditNeighbour]: Adding a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().addNeighbour(l_countryName, l_neighbourName)) {
                    System.out.println("[EditNeighbour]: Added " + l_countryName + " as a neighbour to: " + l_neighbourName);
                } else {
                    System.out.println("[EditNeighbour]: Adding a neighbour failed");
                }
                break;
            case Remove:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 2) {
                    System.out.println("[EditNeighbour]: Removing a neighbour requires 2 parameters\n[1] Country Name\n[2] Neighbour Name");
                    System.out.println("[EditNeighbour]: Removing a neighbour failed");
                    return;
                }
                l_countryName = l_function.functionalityParams[0];
                l_neighbourName = l_function.functionalityParams[1];
                if (Game.sharedInstance().getD_map().removeNeighbour(l_countryName, l_neighbourName)) {
                    System.out.println("[EditNeighbour]: Removed " + l_countryName + " and : " + l_neighbourName + "as neighbours");
                } else {
                    System.out.println("[EditNeighbour]: Removing a neighbour failed");
                }
                break;

            default:
                System.out.println("[EditNeighbour]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
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

            Game.sharedInstance().setD_gamePhase(new PlaySetup());
            System.out.println("[PostLoad]: --- Moving to PlaySetup ---");
        } catch (IOException error) {
            System.out.println("[SaveMapCommand]: Error while saving map to: " + l_filename + " " + error.getLocalizedMessage());
            throw new RuntimeException(error);
        } catch (MapInvalidException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void next() {
        System.out.println("must load map");
    }

    /**
     *
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        System.out.println("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        System.out.println("1. editcontinent [-add continentID continentvalue] [-remove continentID]");
        System.out.println("2. editcountry [-add countryID continentID] [-remove countryID]");
        System.out.println("3. editneighbor [-add countryID neighborcountryID] [-remove countryID]");
        System.out.println("4. showmap");
        System.out.println("5. savemap [filename]");
        System.out.println("6. validatemap");
        System.out.println(" --- ");
    }
}
