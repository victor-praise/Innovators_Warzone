package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  This class is responsible for the map
 * @author Victor
 *
 *
 */
public class Map {
    /**
     * stores the map file name.
     */
    String d_mapFile;
    List<Continent> d_continents;
    List<Country> d_countries;

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
     * adds the country to the map.
     *
     * @param p_country country to add
     */
    public void addCountryToMap(Country p_country){
        d_countries.add(p_country);
    }

    /**
     * adds the continent to the map.
     *
     * @param p_continent continent to add
     */
    public void addContinentToMap(Continent p_continent){
        d_continents.add(p_continent);
    }

    /**
     * Get a List of all Ids of continents in Map.
     *
     * @return List of Continent Ids
     */
    public List<Integer> getContinentIDs(){
        return d_continents.stream()
                .map(Continent::getD_continentID)
                .collect(Collectors.toList());
    }

    /**
     * Get a list of all Ids of countries in Map.
     *
     * @return List of Country Ids
     */
    public List<Integer> getCountryIDs(){
        return d_countries.stream()
                .map(Country::getD_countryID)
                .collect(Collectors.toList());
    }

    /**
     * Validates map.
     *
     * @return Bool Value if map is valid
     */
    public Boolean validate(){
        //should be updated
        return true;
    }

    /**
     * Ensure continent is not already in the map.
     *
     * @param p_continent List of Continents in the map
     * @param p_continentName name of new continent
     */
    public boolean continentExists(List<Continent> p_continent, String p_continentName){
        for (Continent obj : p_continent) {
            if (obj.getD_continentName().equals(p_continentName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns continent given continent name.
     *
     * @param p_continentName name of new continent
     */
    public Continent getContinent(String p_continentName){
        for (Continent obj : d_continents) {
            if (obj.getD_continentName().equals(p_continentName)) {
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
        for (Country c: d_countries) {
            if (c.getD_neighbors()!=null) {
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
    public void updateNeighbours(Integer p_countryId){
        for(Continent c: d_continents){
            c.removeCountryAsNeighbourFromAll(p_countryId);
        }
    }
    /**
     * Performs Add Continent operation on Map.
     *
     * @param p_continentName Name of the Continent to be Added
     * @param p_controlValue Control value of the continent to be added
     */
    public void addContinent(String p_continentName, Integer p_controlValue){
        if(d_continents!=null){
            if(!continentExists(d_continents,p_continentName)){
                d_continents.add(new Continent( p_continentName, p_controlValue));
            }
            else{
                System.out.println("Continent already exists on the map");
            }
        }

        else{
            d_continents= new ArrayList<Continent>();
            d_continents.add(new Continent(p_continentName, p_controlValue));
        }
    }
    /**
     * Removes continent from Map.
     *     Deletes Specified Continent
     *     Deletes Countries in Continents and their associated data in the Map
     *
     * @param p_continentName Continent Name to be removed

     */
    public void removeContinent(String p_continentName){
        if(d_continents!=null){
            if(continentExists(d_continents,p_continentName)){
                Continent l_continent = getContinent(p_continentName);
                if(l_continent != null && l_continent.getD_countries()!=null){
                    for(Country c: l_continent.getD_countries() ){
                        removeCountryAsNeighbourFromAll(c.getD_countryID());
                        updateNeighbours(c.getD_countryID());
                        d_countries.remove(c);
                    }
                }
                d_continents.remove(l_continent);
            }
            else{
                System.out.println("Continent is not on the map");
            }
        }
    }
}
