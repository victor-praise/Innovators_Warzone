package main.java.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes various types of strategies possible.
 *
 * @author kevin on 2023-11-24
 * @version 1.3
 */
public enum Strategy {
    /**
     * Requires user interaction to make decisions.
     */
    Human("human"),
    /**
     * Focuses on centralization of forces and then attack
     */
    Aggressive("aggressive"),
    /**
     * Focuses on protecting its weak countries
     */
    Benevolent("benevolent"),
    /**
     * Deploys on a random country, attacks random neighboring countries,
     * and moves armies randomly between its countries.
     */
    random("random"),
    /**
     * Conquers all the immediate neighboring enemy countries,
     * and then doubles the number of armies on its countries that have enemy neighbors.
     */
    cheater("cheater");

    private Strategy(String strategy) {
        this.d_label = strategy;
    }

    /**
     * describes the lower cased string of the expected command
     */
    public final String d_label;
    private static final Map<String, Strategy> From_String = new HashMap<String, Strategy>();

    static {
        for (Strategy strategy : values()) {
            From_String.put(strategy.d_label, strategy);
        }
    }

    /**
     * Converts a given strategy string to the matching enum value, if correct
     *
     * @param p_label: command string input by the user
     * @return A valid corresponding enum for a command, null if command could not be identified
     */
    public static Strategy from(String p_label) {
        Strategy l_determinate = From_String.get(p_label.toLowerCase());
        return l_determinate == null ? Strategy.Human : l_determinate;
    }

    @Override
    public String toString() {
        return d_label;
    }
}
