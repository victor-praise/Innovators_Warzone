package main.java.models;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;
import main.java.models.MapEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Map;
public class MapEx{
    public Continent getContinentEx(String p_continentName) {
        // for (Continent obj : d_continents) {
        //     if (obj.getD_continentName().equalsIgnoreCase(p_continentName)) {
        //         l_continent = obj;
        //         break;
        //     }
        // }
        // return l_continent;
        Continent l_continent = null;
        if (d_continents == null || d_continents.isEmpty()) {
            return l_continent;
        }
        for (Continent obj : d_continents) {
            if (obj.getD_continentName().equalsIgnoreCase(p_continentName)) {
                l_continent = obj;
                break;
            }
        }
        // return getContinentEx(p_continentName, d_continents, l_continent);
        return l_continent;
    }
}