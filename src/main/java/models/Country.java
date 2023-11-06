package main.java.models;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class is responsible for the countries on the map
 * @author Victor
 *
 *
 */
public class Country {

    private int d_noOfArmies;

    private int d_countryID;

    private int d_continentID;

    private String d_countryName;

    private List<Integer> d_neighbors;

    private boolean d_isNeutralTerritory = false;

    /**
     * constructor of this class.
     *
     * @param p_countryID country ID
     * @param p_countryName country name
     * @param p_continentID continent ID
     */

    public Country(int p_countryID, String p_countryName, int p_continentID){
        this.d_countryID = p_countryID;
        this.d_countryName = p_countryName;
        this.d_continentID = p_continentID;
        this.d_neighbors = new ArrayList<>();
        this.d_noOfArmies = 0;
    }

    /**
     * getter method to get the armies.
     * @return armies
     */
    public int getD_noOfArmies() {
        return d_noOfArmies;
    }

    /**
     * setter method to set the armies.
     *
     * @param p_noOfArmies armies
     */
    public void setD_noOfArmies(int p_noOfArmies) {
        this.d_noOfArmies = p_noOfArmies;
    }

    /**
     * getter method that returns country ID.
     *
     * @return country ID
     */
    public int getD_countryID() {
        return d_countryID;
    }

    /**
     * setter method to set the country ID.
     *
     * @param p_countryID country ID
     */
    public void setD_countryID(int p_countryID) {
        this.d_countryID = p_countryID;
    }

    /**
     * getter method that returns continent ID.
     *
     * @return continent ID
     */
    public int getD_continentID() {
        return d_continentID;
    }

    /**
     * setter method to set the continent ID.
     *
     * @param p_continentID continent ID
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * getter method to get the name of the country.
     *
     * @return name of the country
     */
    public String getD_countryName() {
        return d_countryName;
    }

    /**
     * setter method to set the name of the country.
     *
     * @param p_countryName name of the country
     */
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    /**
     * getter method to get the neighbours of the country.
     *
     * @return list of neighbour country IDs
     */
    public List<Integer> getD_neighbors() {
        return d_neighbors;
    }

    /**
     * setter method to set the neighbours of the country.
     *
     * @param p_neighbors list of adjacent country IDs
     */
    public void setD_neighbors(List<Integer> p_neighbors) {
        this.d_neighbors = p_neighbors;
    }

    /**
     * Determines if a country is neutral
     * @return true if the country is neutral, false otherwise
     */
    public boolean isD_isNeutralTerritory() {
        return d_isNeutralTerritory;
    }

    /**
     * Set the isNeutral flag for a country
     * @param d_isNeutralTerritory value to set
     */
    public void setD_isNeutralTerritory(boolean d_isNeutralTerritory) {
        this.d_isNeutralTerritory = d_isNeutralTerritory;
    }

    /**
     * Adds a neighbour to the list
     * @param p_countryID country ID
     */
    public void addNeighbour(int p_countryID) {
        if(!d_neighbors.contains(p_countryID)) {
            d_neighbors.add(p_countryID);
        }
    }

    /**
     * Removes countryID from neighbour list.
     * @param p_countryID country ID
     */
    public void removeNeighbour(int p_countryID) {
        if(d_neighbors.contains(p_countryID)) {
            d_neighbors.remove((Integer) p_countryID);
        }
        else{
            System.out.println("There is no neighbour with the provided ID");
        }
    }

    /**
     * Adds a given number of army units to this country
     *
     * @param p_units units of army to add
     */
    public void addArmyUnits(int p_units) {
        this.d_noOfArmies += p_units;
    }


     * Reduces a given number of army units to this country
     *
     * @param p_units units of army to reduce
     */
    public void reduceArmyUnits(int p_units) {
        this.d_noOfArmies -= p_units;
        if (this.d_noOfArmies < 0) {
            this.d_noOfArmies = 0;
        }
    }

    /**
     * Check if country is a neighbour to Country A (Current Country object)
     * @param p_destCountry country to be checked as neighbour
     */
    public boolean hasAdjacentCountry(Country p_destCountry) {
        return getD_neighbors().contains(p_destCountry.getD_countryID());
    }

    /**
     * Describes the Country - name and id of neighbours
     * @return String description of country
     */
    @Override
    public String toString() {
        String l_description = d_countryID + " " + d_countryName;
        for (int neighbour: d_neighbors) {
            l_description = l_description.concat(" " + neighbour);
        }
        return l_description;
    }
}
