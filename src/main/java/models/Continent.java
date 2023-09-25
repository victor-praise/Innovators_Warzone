package main.java.models;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class is responsible for the continents on the map
 * @author Victor
 *
 *
 */
public class Continent {
    private static int Next_Continent_Id = 0;

    int d_continentID;

    String d_continentName;

    int d_continentValue;

    List<Country> d_countries;

    /**
     * constructor.
     *
     * @param p_continentID continent ID
     * @param p_continentName continent name
     * @param p_continentValue continent value
     */
    public Continent(int p_continentID, String p_continentName, int p_continentValue) {
        this.d_continentID=p_continentID;
        this.d_continentName=p_continentName;
        this.d_continentValue=p_continentValue;
        this.d_countries=new ArrayList<Country>();
    }

    /**
     * Helper constructor which provides the continent id from a static variable
     * @param p_continentName continent name
     * @param p_continentValue continent value
     */
    public Continent(String p_continentName, int p_continentValue) {
        int l_nextId = ++Next_Continent_Id;
        new Continent(l_nextId, p_continentName, p_continentValue);
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
    public void addCountry(Country p_country){
            d_countries.add(p_country);
    }

    /**
     * removes Country from the Continent.
     *
     * @param p_country country to be removed
     */
    public void removeCountry(Country p_country){
        if(d_countries==null){
            System.out.println("Country does not Exist");
        }else {
            d_countries.remove(p_country);
        }
    }
}
