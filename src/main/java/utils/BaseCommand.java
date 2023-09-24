package main.java.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum contains all the commands that can be given by the user/player.
 * @author kevin on 2023-09-21
 * @version 1.0
 */
public enum BaseCommand {
    EditContinent("editcontinent"),
    EditCountry("editcountry"),
    EditNeighbour("editneighbour"),
    SaveMap("savemap"),
    ShowMap("showmap"),
    EditMap("editmap"),
    ValidateMap("validatemap"),
    AssignCountries("assigncountries"),
    None("");

    /**
     * describes the lower cased string of the expected command
     */
    public final String d_label;
    private static final Map<String, BaseCommand> From_String = new HashMap<String, BaseCommand>();
    private BaseCommand(String commandString) {
        this.d_label = commandString;
    }

    static {
        for(BaseCommand command: values()) {
           From_String.put(command.d_label, command);
           if (command.d_label.equals("editneighbour")) {
               // Additional safety for american english
               From_String.put("editneighbor", command);
            }
        }
    }

    /**
     * Converts a given command string to the matching enum value, if correct
     * @param p_label: command string input by the user
     * @return A valid corresponding enum for a command, null if command could not be identified
     */
    public static BaseCommand from(String p_label) {
        BaseCommand l_determinate = From_String.get(p_label.toLowerCase());
        return l_determinate == null ? BaseCommand.None : l_determinate;
    }
}
