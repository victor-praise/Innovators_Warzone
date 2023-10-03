package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for the addition and removal of players.
 * @author Angad
 * @version 1.0
 */
public class Player {
    private List<Country> d_ownedCountries;
    private String d_name;

    public Player() {
        d_ownedCountries = new ArrayList<>();
    }
    
    public Player(String p_inputName) {
        d_ownedCountries = new ArrayList<>();
        d_name=p_inputName;
    }

    /**
     * Getter for name of player
     *
     * @return name of player
     */
    public String getD_name(){
        return d_name;
    }
    
    /**
     * Setter for name
     *
     * @param p_name name to set
     */
    public void setD_name(String p_name){
        d_name = p_name;
    }

    /**
     * Getter for ownedCountries
     *
     * @return list of countries
     */
    public List<Country> getD_ownedCountries() {
        return d_ownedCountries;
    }

    /**
     * Setter for ownedCountries
     *
     * @param p_ownedCountries list of countries to set
     */
    public void setD_ownedCountries(List<Country> p_ownedCountries) {
        this.d_ownedCountries = p_ownedCountries;
    }
}
