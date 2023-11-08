package main.java.models;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods in Map class
 * @author kevin on 2023-09-30
 * @author Karansinh on 2023-10-04
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

    /**
     * All the given counties are neighbour to each other, the result is a success
     * @throws MapInvalidException
     */
    @Test
    public void test_HasAdjacentContinentConnection_AllConnected() throws MapInvalidException {

        Map l_map = new Map();
        // Create a continent and add connected countries
        Continent l_continent = new Continent("Test Continent", 1);
        Country l_country1 = new Country(1, "Country 1", 1);
        Country l_country2 = new Country(2, "Country 2", 1);
        Country l_country3 = new Country(3, "Country 3", 1);

        l_country1.addNeighbour(2);
        l_country1.addNeighbour(3);
        l_country2.addNeighbour(1);
        l_country2.addNeighbour(3);
        l_country3.addNeighbour(1);
        l_country3.addNeighbour(2);

        l_continent.addCountry(l_country1);
        l_continent.addCountry(l_country2);
        l_continent.addCountry(l_country3);

        assertTrue(l_map.hasAdjacentContinentConnection(l_continent));
    }

    /**
     * Given 1 country is always reachable to itself, the result is a success
     * @throws MapInvalidException
     */
    @Test
    public void test_HasAdjacentContinentConnection_SingleCountry() throws MapInvalidException {

        Map l_map = new Map();
        // Create a continent with a single country
        Continent l_continent = new Continent("Test Continent", 1);
        Country l_country1 = new Country(1, "Country 1", 1);
        l_continent.addCountry(l_country1);

        assertTrue(l_map.hasAdjacentContinentConnection(l_continent));
    }

    /**
     * Adds one continent and checks if added successfully, the result is a success
     */
    @Test
    void test_AddContinent() {

        Map l_map = new Map();
        // Test adding a new continent to the map
        String l_continentName = "TestContinent";
        int l_controlValue = 3;

        l_map.addContinent(l_continentName, l_controlValue);

        // Check if the continent was added successfully
        assertTrue(l_map.continentExists(l_map.getD_continents(), l_continentName));
    }

    /**
     * Given an existing country name, and it should return the corresponding Country object, the result is a success.
     */
    @Test
    public void test_GetCountry_ExistingCountry() {
        // Arrange
        Map l_map = new Map();
        Country l_country1 = new Country(1, "Country1", 1);
        Country l_country2 = new Country(2, "Country2", 2);
        l_map.addCountryToMap(l_country1);
        l_map.addCountryToMap(l_country2);

        // Act
        Country l_result = l_map.getCountry("Country1");

        // Assert
        assertEquals(l_country1, l_result);
    }

    /**
     * Given a non-existing country name, and it should return null.
     */
    @Test
    public void test_GetCountry_NonExistingCountry() {
        // Arrange
        Map l_map = new Map();
        Country l_country1 = new Country(1, "Country1", 1);
        Country l_country2 = new Country(2, "Country2", 2);
        l_map.addCountryToMap(l_country1);
        l_map.addCountryToMap(l_country2);

        // Act
        Country l_result = l_map.getCountry("NonExistingCountry");

        // Assert
        assertNull(l_result);
    }

    /**
     * Given a null country name, and it should also return null.
     */
    @Test
    public void test_GetCountry_NullCountryName() {
        // Arrange
        Map l_map = new Map();
        Country l_country1 = new Country(1, "Country1", 1);
        Country l_country2 = new Country(2, "Country2", 2);
        l_map.addCountryToMap(l_country1);
        l_map.addCountryToMap(l_country2);

        // Act
        Country l_result = l_map.getCountry("");

        // Assert
        assertNull(l_result);
    }

    /**
     * Given a map where all countries are connected to each other, and it returns true.
     * @throws MapInvalidException
     */
    @Test
    public void test_HasCountryConnectivity_AllConnected() throws MapInvalidException {
        // Arrange
        Map l_map = new Map();
        Country l_country1 = new Country(1, "Country1", 1);
        Country l_country2 = new Country(2, "Country2", 1);
        Country l_country3 = new Country(3, "Country3", 1);

        // Adding neighbors for all countries
        l_country1.addNeighbour(2);
        l_country1.addNeighbour(3);

        l_country2.addNeighbour(1);

        l_country3.addNeighbour(1);

        l_map.addCountryToMap(l_country1);
        l_map.addCountryToMap(l_country2);
        l_map.addCountryToMap(l_country3);

        // Act
        boolean l_result = l_map.hasCountryConnectivity();

        // Assert
        assertTrue(l_result);
    }

    /**
     * Given the map is empty, it should also return false.
     * @throws MapInvalidException
     */
    @Test
    public void test_HasCountryConnectivity_EmptyMap() throws MapInvalidException {
        // Arrange
        Map l_map = new Map();

        // Act
        boolean l_result = l_map.hasCountryConnectivity();

        // Assert
        assertFalse(l_result);
    }
}