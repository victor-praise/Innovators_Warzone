package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the functionality for EditCountry Command
 * @author kevin on 2023-09-29
 */
class EditCountryCommandTest {
    /**
     * System under test, i.e. EditContinentCommand
     */
    EditCountryCommand d_systemUnderTest;

    /**
     * Create a new EditCountryCommand
     */
    @BeforeEach
    void setUp() {
        d_systemUnderTest = new EditCountryCommand(null, null);
        Continent l_asia = new Continent("Asia", 5);
        Game.sharedInstance().getD_map().setD_continents(Stream.of(l_asia).toList());
    }

    /**
     * Clear the continents & countries list
     */
    @AfterEach
    void tearDown() {
        Game.sharedInstance().getD_map().setD_continents(new ArrayList<>());
        Game.sharedInstance().getD_map().setD_countries(new ArrayList<>());
    }

    /**
     * When 'EditCountry' command is executed with '-add' functionality, we should see 1 country getting added to countries list
     */
    @Test
    void test_givenAContinent_whenAddCommandExecuted_thenCountryAdded() {
        Functionality function = new Functionality(BaseFunctionality.Add, new String[]{"India", "Asia"});
        d_systemUnderTest.functionalities = new Functionality[]{function};

        d_systemUnderTest.execute();

        List<Country> l_countries = Game.sharedInstance().getD_map().getD_countries();
        assertEquals(1, l_countries.size(), "Expected 1 country, found: " + l_countries.size());
    }

    /**
     * When 'EditCountry' command is executed with '-remove' functionality, we should see 1 country getting removed from countries list
     */
    @Test
    void test_givenAContinent_whenRemoveCommandExecuted_thenCountryRemoved() {
        Country l_country = new Country(1, "India", 1);
        Continent l_continent = Game.sharedInstance().getD_map().getContinent("Asia");
        l_continent.addCountry(l_country);
        Game.sharedInstance().getD_map().addCountryToMap(l_country);
        Functionality function = new Functionality(BaseFunctionality.Remove, new String[]{"India"});
        d_systemUnderTest.functionalities = new Functionality[]{function};
        List<Country> l_countries = Game.sharedInstance().getD_map().getD_countries();
        assertEquals(1, l_countries.size(), "Expected 1 country, found: " + l_countries.size());

        d_systemUnderTest.execute();

        l_countries = Game.sharedInstance().getD_map().getD_countries();
        assertEquals(0, l_countries.size(), "Expected 1 country, found: " + l_countries.size());
    }
}