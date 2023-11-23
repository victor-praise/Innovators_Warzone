package main.java.services;


import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class is responsible for the parsing the conquest map file allowing it to be loaded
 * @author Victor
 *
 *
 */
public class ConquestMapReader {
    /**
     * Reads map file and creates a map with contents of file.
     *
     * @param p_map instance of current map
     * @param p_file All Lines in the map document
     */
    public void readConquestMap(Map p_map, List<String> p_file){
        List<Continent> l_continentObjects = parseContinentMapData(p_file);
        List<Country> l_countryObjects = parseCountryMapData(p_file,l_continentObjects);
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
    public List<Continent> parseContinentMapData(List<String> p_file){

        int l_continentsStartIndex = p_file.indexOf("[Continents]") + 1;
        int l_continentsEndIndex = p_file.indexOf("[Territories]") - 2;

        List<String> l_continentLines = p_file.subList(l_continentsStartIndex, l_continentsEndIndex);
        List<Continent> l_continents = new ArrayList<>();

        for (String continentData : l_continentLines) {
            String[] l_parts = continentData.split("=");
            String l_continentName = l_parts[0];
            int l_controlValue = Integer.parseInt(l_parts[1]);
            l_continents.add(new Continent(l_continentName, l_controlValue));

        }

        return l_continents;
    }

    /**
     * Reads the corresponding map file lines and extracts country data.
     *
     * @param p_file All Lines in the map document
     * @return List of countries
     */
    public List<Country> parseCountryMapData(List<String> p_file,List<Continent> p_continentList){
        List<String> l_countryLines = p_file.subList(p_file.indexOf("[Territories]") + 1,
                p_file.size());

        List<Country> l_countriesList = new ArrayList<Country>();
        int l_countryId = 1;
        for (String country : l_countryLines) {

            if(!country.trim().isEmpty()){
                String[] l_countriesData = country.split(",");
                Continent l_continent = p_continentList.stream().filter(l_cont -> l_cont.getD_continentName().equalsIgnoreCase(l_countriesData[3])).findFirst().orElse(null);

                Country l_country = new Country(l_countryId, l_countriesData[0], l_continent.getD_continentID());
                l_countriesList.add(l_country);
                l_countryId++;
            }

        }
        return parseNeighboursData(p_file,l_countriesList);
    }


    /**
     * Links the Countries to their respective neighbors.
     *
     * @param p_country Total Country Objects Initialized
     * @param p_file All Lines in the map document
     * @return List Updated Country Objects
     */
    public List<Country> parseNeighboursData(List<String> p_file,List<Country> p_country){
        String l_matchedCountry = null;
        for (Country l_cont : p_country) {
            for (String l_contStr : p_file) {
                if ((l_contStr.split(",")[0]).equalsIgnoreCase(l_cont.getD_countryName())) {
                    l_matchedCountry = l_contStr;
                    break;
                }
            }
            if (l_matchedCountry.split(",").length > 4) {
                for (int i = 4; i < l_matchedCountry.split(",").length; i++) {
                    String l_finalL_matchedCountry = l_matchedCountry;
                    int l_finalI = i;
                    Country l_country =  p_country.stream().filter(l_continent -> l_continent.getD_countryName().equalsIgnoreCase(l_finalL_matchedCountry.split(",")[l_finalI]))
                            .findFirst().orElse(null);

                    l_cont.getD_neighbors().add(l_country.getD_countryID());
                }
            }
        }
        return p_country;
    }
    /**
     * Links countries to corresponding continents and sets them in object of
     * continent.
     *
     * @param p_countries Country Object
     * @param p_continents Continent Object
     * @return List of updated continents
     */
    public List<Continent> linkCountryToContinent(List<Country> p_countries, List<Continent> p_continents) {
        for (Continent continent : p_continents) {
            for (Country country : p_countries) {
                if (continent.getD_continentID()==country.getD_continentID()) {
                    continent.addCountry(country);
                }
            }
        }
        return p_continents;
    }
}
