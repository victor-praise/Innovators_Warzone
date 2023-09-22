package main.java.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin on 2023-09-21
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

    public final String label;
    private static final Map<String, BaseCommand> FROM_STRING = new HashMap<String, BaseCommand>();
    private BaseCommand(String commandString) {
        this.label = commandString;
    }
    static {
        for(BaseCommand command: values()) {
           FROM_STRING.put(command.label, command);
           if (command.label.equals("editneighbour")) {
               // Additional safety for american english
               FROM_STRING.put("editneighbor", command);
            }
        }
    }

    public static BaseCommand from(String label) {
        BaseCommand determinate = FROM_STRING.get(label.toLowerCase());
        return determinate == null ? BaseCommand.None : determinate;
    }
}
