package main.java.arena;

import main.java.models.Continent;
import main.java.commands.Command;
import main.java.models.Map;
import main.java.utils.CommandParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A singleton class describing all the high level game functionalities and resources
 *
 * @author kevin on 2023-09-25
 * @version 1.0
 */
public class Game {
    /**
     * private instance of single Game class
     */
    private static Game d_sharedInstance = null;

    /**
     * The current Map object in the game.
     */
    private Map d_map = null;

    /**
     * Returns current map being edited or a new map when there are no current map
     *
     * @return current map object
     */
    public Map getD_map() {
        if (d_map != null) {
            return d_map;
        }
        d_map = new Map();
        return d_map;
    }

    /**
     * Attempts to set a new map object. Fails if current map object is not null.
     * Can be used to reset the map object too by passing null.
     *
     * @param p_map new map object to set
     */
    public void setD_map(Map p_map) {
        if (d_map == null || p_map == null) {
            this.d_map = p_map;
            if (p_map == null) {
                Continent.resetNextContinentId();
            }
            return;
        }
        System.out.println("[Game]: Attempting to overwrite existing map object, declined. Please save the current map before editing another.");
    }

    /**
     * constructor being made private to prevent random instance creation, always use sharedInstance()
     */
    private Game() {
    }

    /**
     * We keep a singleton instance of Game which is accessible throughout the game engine
     *
     * @return an existing instance if it exists, otherwise returns a newly created instance
     */
    public static synchronized Game sharedInstance() {
        if (d_sharedInstance == null) {
            d_sharedInstance = new Game();
        }

        return d_sharedInstance;
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
