package main.java.models;

import java.util.Map;
import java.util.*;

/**
 * @author kevin on 2023-11-04
 */
public enum SpecialCard {
    /**
     * A card that gives you ability to bomb opponents territory
     * i.e. destroy half of the armies located on an opponent’s territory
     */
    Bomb("bomb"),
    /**
     * A card that gives you ability to blockade any controlled territory
     * i.e. triple the number of armies on one of the current player’s territories and make it a neutral territory
     */
    Blockade("blockade"),
    /**
     * A card that gives you ability to airlift your army to any territory
     * i.e. advance some armies from one of the current player’s territories to any another territory
     */
    Airlift("airlift"),
    /**
     * A card that gives you ability to negotiate with opponents
     * i.e. advance some armies from one of the current player’s territories to any another territory
     */
    Diplomacy("negotiate"),
    /**
     * A card that gives you ability to bomb opponents territory
     */
    None("none");

    private static final Map<String, SpecialCard> From_String = new HashMap<String, SpecialCard>();


    static {
        for (SpecialCard functionality : values()) {
            From_String.put(functionality.d_label, functionality);
        }
    }

    /**
     * describes the lower cased string of the card
     */
    public final String d_label;

    SpecialCard(String label) {
        this.d_label = label;
    }

    /**
     * This static function creates a corresponding enum from a valid card string
     *
     * @param p_label the card string input by the user
     * @return A corresponding enum for a valid SpecialCard, null for unknown functionality string
     */
    public static SpecialCard from(String p_label) {
        SpecialCard d_determinate = From_String.get(p_label.toLowerCase());
        return d_determinate == null ? SpecialCard.None : d_determinate;
    }

    public static Object[] validValues() {
        List<SpecialCard> allValues = new ArrayList<SpecialCard>(Arrays.asList(values()));
        allValues.remove(SpecialCard.None);
        return allValues.toArray();
    }
}
