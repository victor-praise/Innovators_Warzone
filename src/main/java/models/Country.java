package main.java.models;

import main.java.arena.Game;

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

    private Player d_ownedBy;

    private int d_availableArmyUnits = 0;

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
     * Reset the properties for next tournament round
     */
    public void reset() {
        this.d_noOfArmies = 0;
        this.d_ownedBy = null;
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
     * Gives count of troops available to Advance/Airlift
     *
     * @return  count of troops available to Advance/Airlift
     */
    public int getD_availableArmyUnits() {
        return d_availableArmyUnits;
    }

    /**
     * Sets the count of troops available to Advance/Airlift
     *
     * @param d_availableArmyUnits kill count
     */
    public void setD_availableArmyUnits(int d_availableArmyUnits) {
        this.d_availableArmyUnits = d_availableArmyUnits;
    }

    /**
     * Reset Available Army Units back to total army units. This should be reset after every turn
     */
    public void resetD_availableArmyUnits() {
        this.d_availableArmyUnits = this.d_noOfArmies;
    }

    /**
     * Set the isNeutral flag for a country
     * @param p_isNeutralTerritory value to set
     */
    public void setD_isNeutralTerritory(boolean p_isNeutralTerritory) {
        if (p_isNeutralTerritory) {
            this.d_ownedBy = null;
        }
        this.d_isNeutralTerritory = p_isNeutralTerritory;
    }

    /**
     * get the player who owns this country
     * @return player who owns this country, null if it's neutral territory
     */
    public Player getD_ownedBy() {
        if (this.d_isNeutralTerritory) {
            return null;
        }
        return d_ownedBy;
    }

    /**
     * sets the owner of this country
     * @param d_ownedBy player who should own this country
     */
    public void setD_ownedBy(Player d_ownedBy) {
        this.d_ownedBy = d_ownedBy;
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
        // Also update the available units for deployment
        this.d_availableArmyUnits += p_units;
    }

    /**
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
     * Finds all the neighbours which are not NEUTRAL and owned by some other player
     * @return all enemy countries
     */
    public Country[] getEnemyNeighbours() {
        Player owner = getD_ownedBy();
        if (owner == null) {
            // if this is NEUTRAL or free-city, there are no enemies
            return new Country[0];
        }
        List<Country> enemies = new ArrayList<Country>();
        for (int neighbourId: d_neighbors) {
            Country neighbour = Game.sharedInstance().getD_map().getCountry(neighbourId);
            if (!neighbour.isD_isNeutralTerritory()) {
                Player neighbourOwnedBy = neighbour.getD_ownedBy();
                if (neighbourOwnedBy == null) {
                    enemies.add(neighbour);
                } else {
                    // If this owner and neighbour owner are different, its enemy territory
                    if (!owner.equals(neighbourOwnedBy)) {
                        enemies.add(neighbour);
                    }
                }
            }
        }
        if (enemies.isEmpty()) {
            // This is an edge case where none of the neighbouring countries are enemy
            // Just pick up the first neighbour
            enemies.add(Game.sharedInstance().getD_map().getCountry(d_neighbors.get(d_neighbors.size() - 1)));
        }
        return enemies.toArray(new Country[0]);
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

    /**
     * For a given country, determines if the owner is different
     * @param country country to test
     * @return true if it's owned by someone else, false if NEUTRAL or owned by same owner
     */
    public boolean isHostileForMe(Country country) {
        if (country.d_isNeutralTerritory) {
            return  false;
        }
        String ownerName = getD_ownedBy().getD_name();
        String testCountryOwnerName = country.getD_ownedBy().getD_name();
        return !ownerName.equals(testCountryOwnerName);
    }

    /**
     * Returns all the neighbours which do not have border with any enemy
     * @return neighbours which are safe
     */
    public Country[] safeNeighbours() {
        List<Country> neighbours = getD_neighbors().stream()
                .map(d_countryID -> Game.sharedInstance().getD_map().getCountry(d_countryID))
                .toList();

        return neighbours.stream().filter(country -> {
            return !country.hasAdjacentEnemyTerritory();
        }).toList().toArray(new Country[0]);
    }

    /**
     * Determines if it has at least one standing army or incoming army from deploy
     * @return true if this country has or will be getting at least one unit of army, false otherwise
     */
    public boolean hasMoreThanOneArmyUnit() {
        return (d_noOfArmies + d_availableArmyUnits) > 0;
    }

    /**
     * Determines if this country has at least one neighbour which is hostile
     * @return true if it has one hostile neighbour, false otherwise
     */
    public boolean hasAdjacentEnemyTerritory() {
        boolean hasEnemyAtBorder = false;
        for (int neighbourId: d_neighbors) {
            Player neighbourOwner = Game.sharedInstance().getD_map().getCountry(neighbourId).getD_ownedBy();
            if (neighbourOwner != null && !neighbourOwner.getD_name().equals(d_ownedBy.getD_name())) {
                hasEnemyAtBorder = true;
            }
        }
        return hasEnemyAtBorder;
    }
}
