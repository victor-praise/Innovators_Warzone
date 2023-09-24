package main.java.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum corresponds to all the possible functionality for any given command
 * @author kevin on 2023-09-21
 * @version 1.0
 */
public enum BaseFunctionality {
    Add("add"),
    Remove("remove"),
    None("");

    public final String d_label;
    private static final Map<String, BaseFunctionality> From_String = new HashMap<String, BaseFunctionality>();

    private BaseFunctionality(String label) {
        this.d_label = label;
    }

    static {
        for (BaseFunctionality functionality: values()) {
            From_String.put(functionality.d_label, functionality);
        }
    }

    /**
     * This static function creates a corresponding enum from a valid functionality string
     * @param p_label the functionality string inupt by the user
     * @return A corresponding enum for a valid string, null for unknown functionality string
     */
    public static BaseFunctionality from(String p_label) {
        BaseFunctionality d_determinate = From_String.get(p_label.toLowerCase());
        return d_determinate == null ? BaseFunctionality.None : d_determinate;
    }
}
