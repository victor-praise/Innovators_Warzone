package main.java.models;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
    private String d_mapFile;

    /**
     * list of continents.
     */
    private List<Continent> d_continents;

    /**
     * HashMap of the countries one can reach from the existing position.
     */
    private HashMap<Integer, Boolean> d_countryReachability = new HashMap<Integer, Boolean>();

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
    public Boolean validate() throws MapInvalidException {
        return (checkMapEmptiness() && hasAdjacentContinent() && hasCountryConnectivity());
    }

    /**
     * performs NULL check on MAP object
     */
    public Boolean checkMapEmptiness() throws MapInvalidException {
        if (getD_continents().isEmpty()) {
            throw new MapInvalidException("There should be at least one continent");
        }
        if (getD_countries().isEmpty()) {
            throw new MapInvalidException("Continents should have at least one country.");
        }
        return true;
    }

    /**
     * check all neighbour connectivity on Continent
     *
     * @return Boolean Value if all are connected
     * @throws MapInvalidException if any continent is not Connected
     */
    public Boolean hasAdjacentContinent() throws MapInvalidException {
        for (Continent l_continent : d_continents) {
            continentHasCountry(l_continent);
            if (!hasAdjacentContinentConnection(l_continent)) {
                return false;
            }
        }
        return true;
    }
    
    private void continentHasCountry(Continent continent) throws MapInvalidException {
        if (continent.getD_countries().isEmpty()) {
            throw new MapInvalidException("Each continent must have at least one country. No country found under: " + continent.getD_continentName());
        }
    }
    
    /**
     * Checks if all countries in a given continent are connected and reachable from each other
     * 
     * @param p_continent
     * @return
     * @throws MapInvalidException
     */
    public Boolean hasAdjacentContinentConnection(Continent p_continent) throws MapInvalidException {
        HashMap<Integer, Boolean> l_countryReachabilityStatus = new HashMap<Integer, Boolean>();

        // Initialize country reachability status for the continent's countries
        for (Country country : p_continent.getD_countries()) {
            l_countryReachabilityStatus.put(country.getD_countryID(), false);
        }

        buildContinentReachabilityStatus(p_continent.getD_countries().get(0), l_countryReachabilityStatus, p_continent);

        // Iterate each entry to locate unreachable countries in continent
        for (Entry<Integer, Boolean> entry : l_countryReachabilityStatus.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = getCountry(entry.getKey());
                String l_messageException = l_country.getD_countryName() + " in Continent " + p_continent.getD_continentName() + " is not reachable";
                throw new MapInvalidException(l_messageException);
            }
        }
        return !l_countryReachabilityStatus.containsValue(false);
    }


    /**
     * Compiles reachability status starting from 1 given country under continent.
     *
     * @param p_country          country visited
     * @param p_continentCountry Hashmap of Visited Boolean Values
     * @param p_continent        continent being checked for connectivity
     */
    public void buildContinentReachabilityStatus(Country p_country, HashMap<Integer, Boolean> p_continentCountry, Continent p_continent) {
        p_continentCountry.put(p_country.getD_countryID(), true);
        for (Country country : p_continent.getD_countries()) {
            if (p_country.getD_neighbors().contains(country.getD_countryID())) {
                if (!p_continentCountry.get(country.getD_countryID())) {
                    buildContinentReachabilityStatus(country, p_continentCountry, p_continent);
                }
            }
        }
    }

    /**
     * Checks country connectivity in the map.
     *
     * @return boolean value (true) for all connected countries
     * @throws MapInvalidException which Country is not connected
     */
    public boolean hasCountryConnectivity() throws MapInvalidException {

        if (d_countries == null) {
            return false;
        }

        // Initialize the country reachability status map
        d_countries.forEach(country -> d_countryReachability.put(country.getD_countryID(), false));

        // Build country reachability status starting from the first country
        buildCountryReachabilityStatus(d_countries.get(0));

        // Check for unreachable countries and throw an exception if found
        for (java.util.Map.Entry<Integer, Boolean> entry : d_countryReachability.entrySet()) {
            if (!entry.getValue()) {
                String l_exceptionMessage = getCountry(entry.getKey()).getD_countryName() + " country is not reachable";
                throw new MapInvalidException(l_exceptionMessage);
            }
        }

        // Check if all countries are reachable
        return !d_countryReachability.containsValue(false);
    }

    /**
     * DFS applies iteratively to all entered node
     *
     * @param p_country Country visited first
     * @throws MapInvalidException Exception
     */
    public void buildCountryReachabilityStatus(Country p_country) throws MapInvalidException {
        d_countryReachability.put(p_country.getD_countryID(), true);
        for (Country l_nextCountry : getConnectedCountriesFor(p_country)) {
            if (!d_countryReachability.get(l_nextCountry.getD_countryID())) {
                buildCountryReachabilityStatus(l_nextCountry);
            }
        }
    }

    /**
     * Fetches connected countries for a given country.
     *
     * @param p_country the country for which connected countries are required
     * @return list of connected countries
     * @throws MapInvalidException Exception explaining the invalidity
     */
    public List<Country> getConnectedCountriesFor(Country p_country) throws MapInvalidException {
        List<Integer> l_neighborIds = p_country.getD_neighbors();
        if (l_neighborIds.isEmpty()) {
            throw new MapInvalidException(p_country.getD_countryName() + " doesn't have any neighbouring countries");
        }
        return l_neighborIds.stream().map(this::getCountry).toList();
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
        if (d_countries != null) {
            for (Country country : d_countries) {
                if (country.getD_countryName().equalsIgnoreCase(p_countryName)) {
                    return country;
                }
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

    public Continent getContinent(int p_continentId){
        if (d_continents == null) {
            return null;
        }
        for (Continent obj: d_continents){
            if(p_continentId == obj.getD_continentID()){
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


        if (continentExists(getD_continents(), p_continentName)) {
            System.out.println("Continent already exists on the map");
        } else {
            Continent l_newContinent = new Continent(p_continentName, p_controlValue);
            d_continents.add(l_newContinent);
            System.out.println("[Map]: Inserted a new continent: " + l_newContinent);
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

//    /**
//     * Displays map according to .map file format
//     *
//     * @author Sadiqali
//     */
//
//    public void show() {
//
//        List<Continent> continents = getD_continents();
//        List<Country> countries = getD_countries();
//
//        // Display list of Continents in the format continentName continentValue
//        if (continents.isEmpty()) {
//            return;
//        }
//        System.out.println("\n[Continents]");
//        for (Continent continent : continents) {
//            System.out.println(continent.getD_continentName() + " " + continent.getD_continentValue());
//        }
//
//        System.out.println();
//
//        // Display list of countries in the format countryID countryName ownerName (if available)
//        if (countries.isEmpty()) {
//            return;
//        }
//        System.out.println("[Countries]");
//        for (Country country : countries) {
//            System.out.print(country.getD_countryID() + " " + country.getD_countryName());
//            String l_ownerName = Game.sharedInstance().getOwnerNameForCountryName(country.getD_countryName());
//            if (l_ownerName != null) {
//                System.out.print(" " + l_ownerName);
//            }
//            System.out.println();
//        }
//
//        System.out.println();
//
//        // Display list of Borders
//        System.out.println("\n[Borders]");
//        for (Country country : countries) {
//            StringBuilder neighbors = new StringBuilder(String.valueOf(country.getD_countryID()));
//            for (int neighbor : country.getD_neighbors()) {
//                neighbors.append(" ").append(neighbor);
//            }
//            System.out.println(neighbors);
//        }
//    }

    /**
     * Displays map information in the format of .map file.
     *
     * This method combines the display of continents, countries, and borders.
     * The information is printed to the console.
     *
     * @author Sadiqali
     */
    public void show() {
        displayContinents();
        displayCountries();
        displayBorders();
    }

    /**
     * Displays the list of continents in the format: continentName continentValue.
     */
    private void displayContinents() {
        List<Continent> l_continents = getD_continents();
        if (l_continents.isEmpty()) {
            return;
        }
        System.out.println("\n[Continents]");
        l_continents.forEach(continent -> System.out.println(continent.getD_continentName() + " " + continent.getD_continentValue()));
        System.out.println();
    }

    /**
     * Displays the list of countries in the format: countryID countryName ownerName (if available).
     */
    private void displayCountries() {
        List<Country> l_countries = getD_countries();
        if (l_countries.isEmpty()) {
            return;
        }
        System.out.println("[Countries]");
        l_countries.forEach(country -> {
            System.out.print(country.getD_countryID() + " " + country.getD_countryName());
            String ownerName = Game.sharedInstance().getOwnerNameForCountryName(country.getD_countryName());
            if (ownerName != null) {
                System.out.print(" " + ownerName);
            }
            System.out.println();
        });
        System.out.println();
    }

    /**
     * Displays the list of borders for each country.
     * Each line shows the countryID followed by its neighboring country IDs.
     */
    private void displayBorders() {
        List<Country> l_countries = getD_countries();
        System.out.println("\n[Borders]");
        l_countries.forEach(country -> {
            String neighbors = country.getD_countryID() + " " + country.getD_neighbors().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "));
            System.out.println(neighbors);
        });
    }

    /**
     * Finds the player owning all the countries in this game
     *
     * @return Winning player of the game
     */
    public Player playerOwningAllCountries() {
        Player owner0 = null;

        for (int index = 0; index < getD_countries().size(); index++) {
            Country l_country = getD_countries().get(index);
            Player l_countryOwner = l_country.getD_ownedBy();
            if (l_countryOwner != null && owner0 == null) {
                owner0 = l_countryOwner;
            } else {
                if (l_countryOwner != null && l_countryOwner != owner0) {
                    // We have different owner, stop and return null;
                    return null;
                }
            }
        }

        return owner0;
    }
}
