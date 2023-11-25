package main.java.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum corresponds to all the possible functionality for any given command
 *
 * @author kevin on 2023-09-21
 * @version 1.0
 */
public enum BaseFunctionality {
    /**
     * Functionality which adds some objects depending on the base of the command
     */
    Add("add"),
    /**
     * Functionality which removes some objects depending on the base of the command
     */
    Remove("remove"),
    /**
     * Functionality for tournament to read a list of maps
     */
    MapFiles("m"),
    /**
     * Functionality for tournament to read a list of player strategies
     */
    PlayerStrategies("p"),
    /**
     * Functionality for tournament to read the number of games
     */
    NumberOfGame("g"),
    /**
     * Functionality for tournament to read maximum number of turns
     */
    MaxTurns("d"),
    /**
     * An invalid/unknown functionality corresponds to this
     */
    None("");

    private static final Map<String, BaseFunctionality> From_String = new HashMap<String, BaseFunctionality>();

    static {
        for (BaseFunctionality functionality : values()) {
            From_String.put(functionality.d_label, functionality);
        }
    }

    /**
     * describes the lower cased string of the expected functionality
     */
    public final String d_label;

    BaseFunctionality(String label) {
        this.d_label = label;
    }

    /**
     * This static function creates a corresponding enum from a valid functionality string
     *
     * @param p_label the functionality string inupt by the user
     * @return A corresponding enum for a valid string, null for unknown functionality string
     */
    public static BaseFunctionality from(String p_label) {
        BaseFunctionality d_determinate = From_String.get(p_label.toLowerCase());
        return d_determinate == null ? BaseFunctionality.None : d_determinate;
    }

    @Override
    public String toString() {
        return this.d_label;
    }
}