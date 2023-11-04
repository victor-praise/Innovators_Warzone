package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the functionality for EditMap Command
 * @author kevin on 2023-09-30
 */
class EditMapCommandTest {
    /**
     * System under test, i.e. EditContinentCommand
     */
    EditMapCommand d_systemUnderTest;

    /**
     * Create a new EditCountryCommand
     */
    @BeforeEach
    void setUp() {
        Game.sharedInstance().setD_map(null);
        d_systemUnderTest = new EditMapCommand(null, null);
    }

    /**
     * Clear the continents & countries list
     */
    @AfterEach
    void tearDown() {
        Game.sharedInstance().getD_map().setD_continents(new ArrayList<>());
        Game.sharedInstance().getD_map().setD_countries(new ArrayList<>());
    }

    @Test
    void test_givenMapOfCanada_whenCommandExecuted_thenContinentAndCountriesLoaded() {
        String l_filename = "Canada";
        d_systemUnderTest.d_baseParams = new String[]{l_filename};

        d_systemUnderTest.execute();

        Map testMap = Game.sharedInstance().getD_map();
        assertEquals(6, testMap.getD_continents().size(), "Expected 6 continents, found: " + testMap.getD_continents().size());
        assertEquals(31, testMap.getD_countries().size(), "Expected 31 countries, found: " + testMap.getD_countries().size());

        Continent l_atlantic = testMap.getContinent("Atlantic_Provinces");
        assertEquals(5, l_atlantic.getD_countries().size(), "Expected 5 countries, found: " + l_atlantic.getD_countries().size());
    }
}