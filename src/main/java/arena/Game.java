package main.java.arena;

import main.java.models.Continent;
import main.java.commands.Command;
import main.java.utils.CommandParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A singleton class describing all the high level game functionalities and resources
 * @author kevin on 2023-09-25
 * @version 1.0
 */
public class Game {
    /**
     * private instance of single Game class
     */
    private static Game d_sharedInstance = null;

    /**
     * holds all the references of the continents in the game
     */
    private List<Continent> d_continents;

    /**
     * constructor being made private to prevent random instance creation, always use sharedInstance()
     */
    private Game() {
        d_continents = new ArrayList<>();
    }

    /**
     * We keep a singleton instance of Game which is accessible throughout the game engine
     * @return an existing instance if it exists, otherwise returns a newly created instance
     */
    public static synchronized Game sharedInstance() {
        if (d_sharedInstance == null) {
            d_sharedInstance = new Game();
        }

        return d_sharedInstance;
    }

    /**
     * Inserts an instance of Continent to the continents list
     * @param p_continent the continent to be inserted, must be non-null
     */
    public void insertContinent(Continent p_continent) {
        if (p_continent == null) {
            System.out.println("[Undefined] can not insert a null value to list of continents");
            return;
        }
        String l_nameToValidate = p_continent.getD_continentName();
        List<Continent> l_sameNameContinents = d_continents
                .stream()
                .filter(continent -> continent.getD_continentName().equalsIgnoreCase(l_nameToValidate))
                .toList();
        if (l_sameNameContinents.isEmpty()) {
            d_continents.add(p_continent);
            System.out.println("[Game] Inserted new continent: " + p_continent);
        } else {
            System.out.println("[Game] Inserted failed. Continent with same name exists: " + l_sameNameContinents.get(0));
        }
    }

    /**
     * Removes an instance of Continent from the existing list
     * @param p_continent continent to be removed, must be non-null
     * @return true if instance was successfully removed, false otherwise
     */
    public boolean removeContinent(Continent p_continent) {
        if (p_continent == null) {
            System.out.println("[Undefined] can not remove a null value to list of continents");
            return false;
        }
        System.out.println("[Game] Will remove continent: " + p_continent);
        return d_continents.remove(p_continent);
    }

    /**
     * Removes a continent with a given continent id from the list
     * @param p_continentId an integer id for the continent to remove
     * @return true if instance was successfully removed, false otherwise
     */
    public boolean removeContinentWithId(int p_continentId) {
        Continent l_continentToRemove = null;
        for (Continent l_continent:d_continents) {
            if (l_continent.getD_continentID() == p_continentId) {
                l_continentToRemove = l_continent;
                break;
            }
        }
        return removeContinent(l_continentToRemove);
    }

    /**
     * Removes a continent with a given name
     * @param p_continentName name of continent to remove
     * @return true if instance was successfully removed, false otherwise
     */
    public boolean removeContinentWithName(String p_continentName) {
        Continent l_continentToRemove = null;
        for (Continent l_continent:d_continents) {
            if (l_continent.getD_continentName().equalsIgnoreCase(p_continentName)) {
                l_continentToRemove = l_continent;
                break;
            }
        }
        return removeContinent(l_continentToRemove);
    }

    /**
     * We set up the game play over here with proper description being given to user
     */
    public void setup() {
        System.out.println("--- Game setup ---");
        System.out.println("--- Enter commands to set up the map followed by 'commit' to begin game ---");
        System.out.println("--- At any point, you can give a command  'quit' to exit the game ---");
        String l_nextCommand = "";
        CommandParser l_parser = new CommandParser();

        while (!(l_nextCommand.equals("commit") || l_nextCommand.equals("quit"))) {
            Scanner commandReader = new Scanner(System.in);
            l_nextCommand = commandReader.nextLine();
            Command l_command = l_parser.parseCommandStatement(l_nextCommand);
            if (l_command != null) {
                l_command.execute();
            } else if (!(l_nextCommand.equals("commit") || l_nextCommand.equals("quit"))) {
                System.out.println("[Undefined] Following command could not be understood: " + l_nextCommand);
            }
        }
    }
}
