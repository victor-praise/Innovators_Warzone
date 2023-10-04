package main.java.services;

import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is responsible for the parsing the map file allowing it to be loaded
 *
 * @author Victor
 */
public class MapReader {

    /**
     * Reads map file and creates a map with contents of file.
     *
     * @param p_map  instance of current map
     * @param p_file All Lines in the map document
     */
    public void readMapFile(Map p_map, List<String> p_file) {
        List<Continent> l_continentObjects = parseContinentMapData(p_file);
        List<Country> l_countryObjects = parseCountryMapData(p_file);
        l_continentObjects = linkCountryToContinent(l_countryObjects, l_continentObjects);
        p_map.setD_continents(l_continentObjects);
        p_map.setD_countries(l_countryObjects);
    }

    /**
     * Reads the corresponding map file lines and extracts continent data.
     *
     * @param p_file All Lines in the map document
     * @return List of continents
     */
    public List<Continent> parseContinentMapData(List<String> p_file) {
        List<String> l_continentLines = p_file.subList(
                p_file.indexOf("[continents]") + 1,
                p_file.indexOf("[countries]") - 1);
        List<Continent> l_continents = new ArrayList<Continent>();
        for (String continents : l_continentLines) {
            String[] l_continentData = continents.split(" ");
            l_continents.add(new Continent(l_continentData[0], Integer.parseInt(l_continentData[1])));
        }
        return l_continents;
    }

    /**
     * Reads the corresponding map file lines and extracts continent data.
     *
     * @param p_file All Lines in the map document
     * @return List of countries
     */
    public List<Country> parseCountryMapData(List<String> p_file) {
        List<String> l_countryLines = p_file.subList(p_file.indexOf("[countries]") + 1,
                p_file.indexOf("[borders]") - 1);

        List<Country> l_countriesList = new ArrayList<Country>();

        for (String country : l_countryLines) {
            String[] l_countriesData = country.split(" ");
            int l_countryId = Integer.parseInt(l_countriesData[0]);
            int l_continentId = Integer.parseInt(l_countriesData[2]);
            Country l_country = new Country(l_countryId, l_countriesData[1], l_continentId);
            l_countriesList.add(l_country);
        }
        return parseNeighboursData(p_file, l_countriesList);
    }

    /**
     * Links the Countries to their respective neighbors.
     *
     * @param p_country Total Country Objects Initialized
     * @param p_file    All Lines in the map document
     * @return List Updated Country Objects
     */
    public List<Country> parseNeighboursData(List<String> p_file, List<Country> p_country) {
        List<String> l_bordersLines = p_file.subList(p_file.indexOf("[borders]") + 1,
                p_file.size());
        LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<>();

        for (String l_neighbour : l_bordersLines) {
            if (l_neighbour != null && !l_neighbour.isEmpty()) {
                ArrayList<Integer> l_neighbours = new ArrayList<>();
                String[] l_neighbourData = l_neighbour.split(" ");
                for (int i = 1; i <= l_neighbourData.length - 1; i++) {
                    l_neighbours.add(Integer.parseInt(l_neighbourData[i]));

                }
                l_countryNeighbors.put(Integer.parseInt(l_neighbourData[0]), l_neighbours);
            }
        }
        for (Country c : p_country) {
            List<Integer> l_countryNeighbours = l_countryNeighbors.get(c.getD_countryID());
            c.setD_neighbors(l_countryNeighbours);
        }
        return p_country;
    }

    /**
     * Links countries to corresponding continents and sets them in object of
     * continent.
     *
     * @param p_countries  Country Object
     * @param p_continents Continent Object
     * @return List of updated continents
     */
    public List<Continent> linkCountryToContinent(List<Country> p_countries, List<Continent> p_continents) {
        for (Continent continent : p_continents) {
            for (Country country : p_countries) {
                if (continent.getD_continentID() == country.getD_continentID()) {
                    continent.addCountry(country);
                }
            }
        }
        return p_continents;
    }
}
