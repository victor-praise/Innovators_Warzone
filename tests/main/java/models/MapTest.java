package main.java.models;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods in Map class
 * @author kevin on 2023-09-30
 */
class MapTest {

    /**
     * System under test, i.e. Map
     */
    Map d_systemUnderTest;

    /**
     * Create a new connected continent
     */
    @BeforeEach
    void setUp() {
        d_systemUnderTest = Game.sharedInstance().getD_map();
    }

    /**
     * Clear the continents list
     */
    @AfterEach
    void tearDown() {
        Game.sharedInstance().getD_map().setD_continents(new ArrayList<>());
        Game.sharedInstance().getD_map().setD_countries(new ArrayList<>());
    }

    /**
     * Given a connected continent when map is validated, the result is a success
     */
    @Test
    public void test_givenAConnectedContinent_whenValidated_thenValidationSuccessful() {
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(1, "China", 1);
        l_asia.addCountry(l_country2);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());
        d_systemUnderTest.setD_continents(Stream.of(l_asia).toList());
        d_systemUnderTest.setD_countries(Stream.of(l_country1, l_country2).toList());

        try {
            boolean isValid = d_systemUnderTest.validate();
            assertTrue(isValid, "Continent is supposed to be connected");
        } catch (MapInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given an unconnected continent when map is validated, the result is a failure
     */
    @Test
    public void test_givenAnUnconnectedContinent_whenValidated_thenValidationFails() {
        // continent 1
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(1, "China", 1);
        l_asia.addCountry(l_country2);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());

        // continent 2
        Continent l_europe = new Continent("Europe", 5);
        d_systemUnderTest.setD_continents(Stream.of(l_asia, l_europe).toList());
        d_systemUnderTest.setD_countries(Stream.of(l_country1, l_country2).toList());

        try {
            d_systemUnderTest.validate();
        } catch (MapInvalidException e) {
            String l_expectedMessage = "Each continent must have at least one country. No country found under: Europe";
            assertEquals(l_expectedMessage, e.getMessage());
            assertTrue(true, "Map Invalid Exception should be raised for unconnected continent");
        }
    }
}