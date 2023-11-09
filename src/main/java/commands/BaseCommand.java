package main.java.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum contains all the commands that can be given by the user/player.
 *
 * @author kevin on 2023-09-21
 * @version 1.0
 */
public enum BaseCommand {
    /**
     * Command to add/remove a continent
     */
    EditContinent("editcontinent"),

    /**
     * Command to add/remove a country in a continent.
     */
    EditCountry("editcountry"),

    /**
     * Command to add/remove a country from the neighbour's list of another country
     */
    EditNeighbour("editneighbour"),

    /**
     * Command to save the current state of map to a .map file
     */
    SaveMap("savemap"),

    /**
     * Command to display a map in full details. i.e. it's continents, countries, their neighbours etc.
     */
    ShowMap("showmap"),

    /**
     * Loads a map from a given file, or from command line when no filename provided.
     */
    EditMap("editmap"),

    /**
     * Command to validate a map up till the point when this command is executed
     */
    ValidateMap("validatemap"),

    /**
     * Distribute all the countries at random to all the players
     */
    AssignCountries("assigncountries"),

    /**
     * To add or remove a player
     */
    GamePlayer("gameplayer"),

    /**
     * Loads a map from a given file
     */
    LoadMap("loadmap"),

    /**
     * Issues a deploy order
     */
    Deploy("deploy"),

    /**
     * Issues a Diplomacy Order
     */
    Diplomacy("negotiate"),

    /**
     * Issues a commit order when there are no more orders
     */
    Commit("commit"),

    /**
     * Issues an advance order
     */
    Advance("advance"),

    /**
     * Issues a deploy order
     */
    Quit("quit"),

    /**
     * Airlift XX army units from one territory to another.
     */
    Airlift("airlift"),

    /**
     * Marks a given territory as NEUTRAL and tripling its army units.
     */
    Blockade("blockade"),


    /**
     * destroy half of the armies located on an opponent’s territory.
     */
    Bomb("bomb"),
    /**
     * An invalid command string by user will correspond to this.
     */
    None("");

    private static final Map<String, BaseCommand> From_String = new HashMap<String, BaseCommand>();

    static {
        for (BaseCommand command : values()) {
            From_String.put(command.d_label, command);
            if (command.d_label.equals("editneighbour")) {
                // Additional safety for american english
                From_String.put("editneighbor", command);
            }
        }
    }

    /**
     * describes the lower cased string of the expected command
     */
    public final String d_label;

    private BaseCommand(String commandString) {
        this.d_label = commandString;
    }

    /**
     * Converts a given command string to the matching enum value, if correct
     *
     * @param p_label: command string input by the user
     * @return A valid corresponding enum for a command, null if command could not be identified
     */
    public static BaseCommand from(String p_label) {
        BaseCommand l_determinate = From_String.get(p_label.toLowerCase());
        return l_determinate == null ? BaseCommand.None : l_determinate;
    }
}
