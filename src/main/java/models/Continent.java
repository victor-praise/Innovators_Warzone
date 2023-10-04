package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for the continents on the map
 *
 * @author Victor
 * @version 1.0
 */
public class Continent {
    private static int Next_Continent_Id = 0;

    private int d_continentID;

    private String d_continentName;

    private int d_continentValue;

    private List<Country> d_countries;

    /**
     * constructor.
     *
     * @param p_continentID    continent ID
     * @param p_continentName  continent name
     * @param p_continentValue continent value
     */
    public Continent(int p_continentID, String p_continentName, int p_continentValue) {
        this.d_continentID = p_continentID;
        this.d_continentName = p_continentName;
        this.d_continentValue = p_continentValue;
        this.d_countries = new ArrayList<Country>();
    }

    /**
     * Helper constructor which provides the continent id from a static variable
     *
     * @param p_continentName  continent name
     * @param p_continentValue continent value
     */
    public Continent(String p_continentName, int p_continentValue) {
        this(++Next_Continent_Id, p_continentName, p_continentValue);
    }

    /**
     * When ever a map is set to null, Next_Continent_Id should default back to 0
     */
    public static void resetNextContinentId() {
        Next_Continent_Id = 0;
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
     * setter method to set the continenet ID.
     *
     * @param p_continentID continent ID
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * getter method that returns continent name.
     *
     * @return continent name
     */
    public String getD_continentName() {
        return d_continentName;
    }

    /**
     * setter method to set the continent name.
     *
     * @param p_continentName name of the continent
     */
    public void setD_continentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    /**
     * getter method that returns continent value.
     *
     * @return continent value
     */
    public int getD_continentValue() {
        return d_continentValue;
    }

    /**
     * setter method to set the continent value.
     *
     * @param p_continentValue the continent value
     */
    public void setD_continentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    /**
     * getter method that returns a list of countries.
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
     * adds a country to the continent.
     *
     * @param p_country the country to be added
     */
    public void addCountry(Country p_country) {
        d_countries.add(p_country);
    }

    /**
     * removes Country from the Continent.
     *
     * @param p_country country to be removed
     */
    public void removeCountry(Country p_country) {
        if (d_countries == null) {
            System.out.println("Country does not Exist");
        } else {
            d_countries.remove(p_country);
        }
    }

    /**
     * Removes particular country ID from the neighbor list of all countries in continent.
     *
     * @param p_countryId ID of country to be removed
     */
    public void removeCountryAsNeighbourFromAll(Integer p_countryId) {
        if (d_countries != null && !d_countries.isEmpty()) {
            for (Country c : d_countries) {
                if (c.getD_neighbors() != null) {
                    if (c.getD_neighbors().contains(p_countryId)) {
                        c.removeNeighbour(p_countryId);
                    }
                }
            }
        }
    }

    /**
     * Describes the Continent - name and bonus points
     *
     * @return String description
     */
    @Override
    public String toString() {
        String l_description = "Name: ";
        return "\nName: "
                .concat(this.getD_continentName())
                .concat("\n")
                .concat("Bonus Points: " + this.getD_continentValue());
    }
}
