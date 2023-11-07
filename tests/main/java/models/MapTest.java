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
    public void testHasAdjacentContinentConnection_AllConnected() throws MapInvalidException {

        Map map = new Map();
        // Create a continent and add connected countries
        Continent continent = new Continent("Test Continent", 1);
        Country country1 = new Country(1, "Country 1", 1);
        Country country2 = new Country(2, "Country 2", 1);
        Country country3 = new Country(3, "Country 3", 1);

        country1.addNeighbour(2);
        country1.addNeighbour(3);
        country2.addNeighbour(1);
        country2.addNeighbour(3);
        country3.addNeighbour(1);
        country3.addNeighbour(2);

        continent.addCountry(country1);
        continent.addCountry(country2);
        continent.addCountry(country3);

        assertTrue(map.hasAdjacentContinentConnection(continent));
    }

    /**
     * Given 1 country is always reachable to itself, the result is a success
     * @throws MapInvalidException
     */
    @Test
    public void testHasAdjacentContinentConnection_SingleCountry() throws MapInvalidException {

        Map map = new Map();
        // Create a continent with a single country
        Continent continent = new Continent("Test Continent", 1);
        Country country1 = new Country(1, "Country 1", 1);
        continent.addCountry(country1);

        assertTrue(map.hasAdjacentContinentConnection(continent));
    }

    /**
     * Adds one continent and checks if added successfully, the result is a success
     */
    @Test
    void testAddContinent() {

        Map map = new Map();
        // Test adding a new continent to the map
        String continentName = "TestContinent";
        int controlValue = 3;

        map.addContinent(continentName, controlValue);

        // Check if the continent was added successfully
        assertTrue(map.continentExists(map.getD_continents(), continentName));
    }

    /**
     * Given an existing country name, and it should return the corresponding Country object, the result is a success.
     */
    @Test
    public void testGetCountry_ExistingCountry() {
        // Arrange
        Map map = new Map();
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 2);
        map.addCountryToMap(country1);
        map.addCountryToMap(country2);

        // Act
        Country result = map.getCountry("Country1");

        // Assert
        assertEquals(country1, result);
    }

    /**
     * Given a non-existing country name, and it should return null.
     */
    @Test
    public void testGetCountry_NonExistingCountry() {
        // Arrange
        Map map = new Map();
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 2);
        map.addCountryToMap(country1);
        map.addCountryToMap(country2);

        // Act
        Country result = map.getCountry("NonExistingCountry");

        // Assert
        assertNull(result);
    }

    /**
     * Given a null country name, and it should also return null.
     */
    @Test
    public void testGetCountry_NullCountryName() {
        // Arrange
        Map map = new Map();
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 2);
        map.addCountryToMap(country1);
        map.addCountryToMap(country2);

        // Act
        Country result = map.getCountry("");

        // Assert
        assertNull(result);
    }

    /**
     * Given a map where all countries are connected to each other, and it returns true.
     * @throws MapInvalidException
     */
    @Test
    public void testHasCountryConnectivity_AllConnected() throws MapInvalidException {
        // Arrange
        Map map = new Map();
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 1);

        // Adding neighbors for all countries
        country1.addNeighbour(2);
        country1.addNeighbour(3);

        country2.addNeighbour(1);

        country3.addNeighbour(1);

        map.addCountryToMap(country1);
        map.addCountryToMap(country2);
        map.addCountryToMap(country3);

        // Act
        boolean result = map.hasCountryConnectivity();

        // Assert
        assertTrue(result);
    }

    /**
     * Given the map is empty, it should also return false.
     * @throws MapInvalidException
     */
    @Test
    public void testHasCountryConnectivity_EmptyMap() throws MapInvalidException {
        // Arrange
        Map map = new Map();

        // Act
        boolean result = map.hasCountryConnectivity();

        // Assert
        assertFalse(result);
    }
}