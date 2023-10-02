package main.java.models;

import main.java.exceptions.InValidException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for the map
 *
 * @author Victor
 */
public class Map {
    /**
     * stores the map file name.
     */
    String d_mapFile;

    /**
     * list of continents.
     */
    List<Continent> d_continents;

    /**
     * HashMap of the countries one can reach from the existing position.
     */
    HashMap<Integer, Boolean> d_countryReach = new HashMap<Integer, Boolean>();

    /**
     * list of countries.
     */
    private List<Country> d_countries;

    /**
     * getter method that returns the map file.
     *
     * @return d_mapfile
     */
    public String getD_mapFile() {
        return d_mapFile;
    }

    /**
     * setter method to set the map file.
     *
     * @param p_mapFile mapfile name
     */
    public void setD_mapFile(String p_mapFile) {
        this.d_mapFile = p_mapFile;
    }

    /**
     * getter method that returns the list of continents.
     *
     * @return d_continents
     */
    public List<Continent> getD_continents() {
        if (d_continents == null) {
            d_continents = new ArrayList<Continent>();
        }
        return d_continents;
    }

    /**
     * setter method to set the list of continents.
     *
     * @param p_continents list of continents
     */
    public void setD_continents(List<Continent> p_continents) {
        this.d_continents = p_continents;
    }

    /**
     * getter method that returns the list of countries.
     *
     * @return list of countries
     */
    public List<Country> getD_countries() {
        if (d_countries == null) {
            d_countries = new ArrayList<Country>();
        }
        return d_countries;
    }

    /**
     * setter method to set the countries.
     *
     * @param p_countries list of countries
     */
    public void setD_countries(List<Country> p_countries) {
        this.d_countries = p_countries;
    }

    /**
     * Checks for null and empty continents list
     *
     * @return true if map has at-least one continent, false otherwise
     */
    public boolean hasContinents() {
        return d_continents != null && !d_continents.isEmpty();
    }

    /**
     * Checks for null and empty continents list
     *
     * @return true if map has at-least one continent, false otherwise
     */
    public boolean hasCountries() {
        return d_countries != null && !d_countries.isEmpty();
    }

    /**
     * adds the country to the map.
     *
     * @param p_country country to add
     */
    public void addCountryToMap(Country p_country) {
        getD_countries().add(p_country);
    }

    /**
     * adds the continent to the map.
     *
     * @param p_continent continent to add
     */
    public void addContinentToMap(Continent p_continent) {

        d_continents.add(p_continent);
    }

    /**
     * Get a List of all Ids of continents in Map.
     *
     * @return List of Continent Ids
     */
    public List<Integer> getContinentIDs() {
        return d_continents.stream()
                .map(Continent::getD_continentID)
                .collect(Collectors.toList());
    }

    /**
     * Get a list of all Ids of countries in Map.
     *
     * @return List of Country Ids
     */
    public List<Integer> getCountryIDs() {
        return d_countries.stream()
                .map(Country::getD_countryID)
                .collect(Collectors.toList());
    }

    /**
     * Validates map.
     *
     * @return Bool Value if map is valid
     */
    public Boolean validate() throws InValidException {
        return (isObjectNotNull() && hasAdjacentContinent() && hasCountryConnectivity());
    }

    /**
     * performs NULL check on MAP object
     */
    public Boolean isObjectNotNull() throws InValidException {
        if (d_continents == null || d_continents.isEmpty()) {
            throw new InValidException("Map continent cannot be empty.");
        }
        if (d_countries == null || d_countries.isEmpty()) {
            throw new InValidException("Each continent must have one country.");
        }
        return true;
    }

    /**
     * check all neighbour connectivity on Continent
     *
     * @return Boolean Value if all are connected
     * @throws InValidException if any continent is not Connected
     */
    public Boolean hasAdjacentContinent() throws InValidException {
        for (Continent l_continent : d_continents) {
            if (l_continent.getD_countries().isEmpty()) {
                throw new InValidException(l_continent.getD_continentName() + " has no countries. It must have at least one country");
            }
            if (!hasAdjacentContinentConnection(l_continent)) {
                return false;
            }
        }
        return true;
    }

    public Boolean hasAdjacentContinentConnection(Continent p_continent) throws InValidException {
        HashMap<Integer, Boolean> l_continentCountry = new HashMap<Integer, Boolean>();
        for (Country country : p_continent.getD_countries()) {
            l_continentCountry.put(country.getD_countryID(), false);
        }
        dfsSubgraph(p_continent.getD_countries().get(0), l_continentCountry, p_continent);

        // Iterate each entry to locate unreachable countries in continent
        for (java.util.Map.Entry<Integer, Boolean> entry : l_continentCountry.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = getCountry(entry.getKey());
                String l_messageException = l_country.getD_countryName() + " in Continent " + p_continent.getD_continentName() + " is not reachable";
                throw new InValidException(l_messageException);
            }
        }
        return !l_continentCountry.containsValue(false);
    }


    /**
     * DFS Applied to the Countries under continent.
     *
     * @param p_c                country visited
     * @param p_continentCountry Hashmap of Visited Boolean Values
     * @param p_continent        continent being checked for connectivity
     */
    public void dfsSubgraph(Country p_c, HashMap<Integer, Boolean> p_continentCountry, Continent p_continent) {
        p_continentCountry.put(p_c.getD_countryID(), true);
        for (Country country : p_continent.getD_countries()) {
            if (p_c.getD_neighbors().contains(country.getD_countryID())) {
                if (!p_continentCountry.get(country.getD_countryID())) {
                    dfsSubgraph(country, p_continentCountry, p_continent);
                }
            }
        }
    }


    /**
     * Checks country connectivity in the map.
     *
     * @return boolean value (true) for all connected countries
     * @throws InValidException which Country is not connected
     */
    public boolean hasCountryConnectivity() throws InValidException {
        for (Country country : d_countries) {
            d_countryReach.put(country.getD_countryID(), false);
        }

        dfsCountry(d_countries.get(0));

        // Iterates over entries to locate the unreachable country
        for (java.util.Map.Entry<Integer, Boolean> entry : d_countryReach.entrySet()) {
            if (!entry.getValue()) {
                String l_exceptionMessage = getCountry(entry.getKey()).getD_countryName() + " country is not reachable";
                throw new InValidException(l_exceptionMessage);
            }
        }
        return !d_countryReach.containsValue(false);
    }


    /**
     * DFS applies iteratively to all entered node
     *
     * @param p_c Country visited first
     * @throws InValidException Exception
     */
    public void dfsCountry(Country p_c) throws InValidException {
        d_countryReach.put(p_c.getD_countryID(), true);
        for (Country l_nextCountry : getAdjacentCountry(p_c)) {
            if (!d_countryReach.get(l_nextCountry.getD_countryID())) {
                dfsCountry(l_nextCountry);
            }
        }
    }

    /**
     * Gets the Adjacent Country Objects.
     *
     * @param p_country the adjacent country
     * @return list of Adjacent Country Objects
     * @throws InValidException pointing out which Country is not connected
     * @throws InValidException Exception
     */
    public List<Country> getAdjacentCountry(Country p_country) throws InValidException {
        List<Country> l_adjCountries = new ArrayList<Country>();
        if (!p_country.getD_neighbors().isEmpty()) {
            for (int i : p_country.getD_neighbors()) {
                l_adjCountries.add(getCountry(i));
            }
        } else {
            throw new InValidException(p_country.getD_countryName() + " doesn't have any neighbour countries");
        }
        return l_adjCountries;
    }

    /**
     * Finds the Country object from a given country ID.
     *
     * @param p_countryId ID of the country object to be found
     * @return matching country object
     */
    public Country getCountry(Integer p_countryId) {
        return d_countries.stream().filter(l_country -> l_country.getD_countryID() == p_countryId).findFirst().orElse(null);
    }

    /**
     * Ensure continent is not already on the map.
     *
     * @param p_continent     List of Continents in the map
     * @param p_continentName name of new continent
     * @return true if the continent exists, false otherwise
     */
    public boolean continentExists(List<Continent> p_continent, String p_continentName) {
        for (Continent obj : p_continent) {
            if (obj.getD_continentName().equalsIgnoreCase(p_continentName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure country is not already on the map.
     *
     * @param p_countryName name of new continent
     * @return true if the country exists, false otherwise
     */
    public boolean countryExists(String p_countryName) {
        for (Country obj : getD_countries()) {
            if (obj.getD_countryName().equalsIgnoreCase(p_countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns continent given continent name.
     *
     * @param p_continentName name of new continent
     * @return Continent with the passed in name, null when no continent could not be found
     */
    public Continent getContinent(String p_continentName) {
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
        return l_continent;
    }

    /**
     * returns continent given continent name.
     *
     * @param p_countryName name of new continent
     * @return Country with the passed in name, null when no country could not be found
     */
    public Country getCountry(String p_countryName) {
        if (d_countries == null) {
            return null;
        }
        for (Country obj : d_countries) {
            if (obj.getD_countryName().equalsIgnoreCase(p_countryName)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * returns the country for a given country id.
     *
     * @param p_countryId id of a country
     * @return Country with the passed in id, null when no country could not be found
     */
    public Country getCountry(int p_countryId) {
        if (d_countries == null) {
            return null;
        }
        for (Country obj : d_countries) {
            if (p_countryId == obj.getD_countryID()) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Remove Specified Country as a Neighbor from all countries on the map
     *
     * @param p_countryID Country to be removed
     */
    public void removeCountryAsNeighbourFromAll(Integer p_countryID) {
        for (Country c : d_countries) {
            if (c.getD_neighbors() != null) {
                if (c.getD_neighbors().contains(p_countryID)) {
                    c.removeNeighbour(p_countryID);
                }
            }
        }
    }

    /**
     * Remove Particular Country as Neighbor from all associated countries (in continent Objects)
     *
     * @param p_countryId Country to be removed
     */
    public void updateNeighbours(Integer p_countryId) {
        for (Continent c : d_continents) {
            c.removeCountryAsNeighbourFromAll(p_countryId);
        }
    }

    /**
     * Performs Add Continent operation on Map.
     *
     * @param p_continentName Name of the Continent to be Added
     * @param p_controlValue  Control value of the continent to be added
     */
    public void addContinent(String p_continentName, Integer p_controlValue) {
        if (!continentExists(getD_continents(), p_continentName)) {
            Continent l_newContinent = new Continent(p_continentName, p_controlValue);
            d_continents.add(l_newContinent);
            System.out.println("[Map]: Inserted a new continent: " + l_newContinent);
        } else {
            System.out.println("Continent already exists on the map");
        }
    }

    /**
     * Removes continent from Map.
     * Deletes Specified Continent
     * Deletes all Countries in the Continent along with their data on the Map
     *
     * @param p_continentName Continent Name to be removed
     * @return true when the continent was removed successfully, false otherwise
     */
    public boolean removeContinent(String p_continentName) {
        if (d_continents != null) {
            if (continentExists(d_continents, p_continentName)) {
                Continent l_continent = getContinent(p_continentName);
                if (l_continent != null && l_continent.getD_countries() != null) {
                    for (Country c : l_continent.getD_countries()) {
                        removeCountryAsNeighbourFromAll(c.getD_countryID());
                        updateNeighbours(c.getD_countryID());
                        d_countries.remove(c);
                    }
                }
                return d_continents.remove(l_continent);
            }
        }
        System.out.println("Continent with name " + p_continentName + "is not on the map");
        return false;
    }

    /**
     * adds a country to the Map.
     *
     * @param p_countryName   Name of Country to be added
     * @param p_continentName Name of Continent to be added
     */
    public void addCountry(String p_countryName, String p_continentName) {
        if (d_countries == null) {
            d_countries = new ArrayList<>();
        }
        if (!countryExists(p_countryName)) {
            int l_countryId = !d_countries.isEmpty() ? Collections.max(getCountryIDs()) + 1 : 1;
            Continent l_continent = getContinent(p_continentName);
            if (l_continent != null) {
                Country l_country = new Country(l_countryId, p_countryName, l_continent.getD_continentID());
                d_countries.add(l_country);
                l_continent.addCountry(l_country);
                System.out.println("[Map]: " + p_countryName + " added to Continent of " + l_continent.getD_continentName());
            } else {
                System.out.println("[Map]: Continent does not exist");
            }
        } else {
            System.out.println("[Map]: Country already exists: " + p_countryName);
        }
    }

    /**
     * removes a country from the Map.
     *
     * @param p_countryName Name of country to be Added
     * @return true when the country was removed successfully, false otherwise
     */
    public boolean removeCountry(String p_countryName) {
        Country l_country = getCountry(p_countryName);
        if (d_countries == null || l_country == null) {
            return false;
        }
        for (Continent l_continent : d_continents) {
            if (l_continent.getD_continentID() == l_country.getD_continentID()) {
                l_continent.removeCountry(l_country);
            }
            l_continent.removeCountryAsNeighbourFromAll(l_country.getD_countryID());
        }
        removeCountryAsNeighbourFromAll(l_country.getD_countryID());
        return d_countries.remove(l_country);
    }

    /**
     * Adds a country as a Neighbour to another.
     *
     * @param p_countryName   Country whose neighbours are to be updated
     * @param p_neighbourName Country to be added as neighbour
     * @return true when the neighbour was added successfully, false otherwise
     */
    public boolean addNeighbour(String p_countryName, String p_neighbourName) {
        Country l_country = getCountry(p_countryName);
        Country l_neighbour = getCountry(p_neighbourName);

        if (l_country != null && l_neighbour != null) {
            // Add both as neighbour to each other
            l_country.addNeighbour(l_neighbour.getD_countryID());
            l_neighbour.addNeighbour(l_country.getD_countryID());
            return true;
        } else {
            System.out.println("[Map]: At least one of these countries do not exist");
            return false;
        }
    }

    /**
     * Removes a country as a Neighbor to another.
     *
     * @param p_countryName   Country whose neighbors are to be updated
     * @param p_neighbourName Country to be removed as neighbor
     * @return true when the neighbour was removed successfully, false otherwise
     */
    public boolean removeNeighbour(String p_countryName, String p_neighbourName) {
        if (getCountry(p_countryName) != null && getCountry(p_neighbourName) != null) {
            Country l_country = getCountry(p_countryName);
            Country l_neighbour = getCountry(p_neighbourName);

            // Remove both as neighbour to each other
            l_country.removeNeighbour(l_neighbour.getD_countryID());
            l_neighbour.removeNeighbour(l_country.getD_countryID());
            return true;
        } else {
            System.out.println("[Map]: At least one of these countries do not exist");
            return false;
        }
    }

    /**
     * Displays map according to .map file format
     *
     * @author Sadiqali
     */

    public void show() {

        System.out.println("\n[Continents]");

        /**
         * Displayes list of Continents in the format continentName continentValue
         */

        if (getD_continents().isEmpty()) {
            return;
        }
        for (Continent continent : getD_continents()) {
            System.out.print(continent.getD_continentName());
            System.out.print(" " + continent.getD_continentValue() + "\n");
        }

        System.out.println();

        /**
         * Displays list of countries in the format countryID countryName
         */

        if (!getD_countries().isEmpty()) {
            System.out.println("[Countries]");
            for (Country country : getD_countries()) {
                System.out.print("" + country.getD_countryID());
                System.out.print(" " + country.getD_countryName());
                System.out.print(" " + country.getD_continentID() + "\n");
            }
        } else {
            return;
        }

        System.out.print("\n");

        if (getD_countries().isEmpty()) {
            return;
        }
        System.out.println("\n[Borders]");
        for (Country country : getD_countries()) {
            String neighbours = String.valueOf(country.getD_countryID());
            for (int neighbor : country.getD_neighbors()) {
                neighbours += (" " + neighbor);
            }
            System.out.println(neighbours);
        }
    }
}
