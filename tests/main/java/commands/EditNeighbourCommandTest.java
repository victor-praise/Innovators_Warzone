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
 * @author kevin on 2023-09-30
 */
class EditNeighbourCommandTest {
    /**
     * System under test, i.e. EditContinentCommand
     */
    EditNeighbourCommand d_systemUnderTest;

    /**
     * Create a new EditContinentCommand
     */
    @BeforeEach
    void setUp() {
        d_systemUnderTest = new EditNeighbourCommand(null, null);
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(1, "China", 1);
        l_asia.addCountry(l_country2);
        Game.sharedInstance().getD_map().setD_continents(Stream.of(l_asia).toList());
        Game.sharedInstance().getD_map().setD_countries(Stream.of(l_country1, l_country2).toList());
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
     * When a country1 is added as neighbour to country2,
     * it is expected that country2 will automatically have country1 as neighbour
     */
    @Test
    void given2Countries_whenCountry2AddedAsNeighbourToCountry1_thenCountry2AlsoHasNeighbour() {
        Functionality function = new Functionality(BaseFunctionality.Add, new String[]{"India", "China"});
        d_systemUnderTest.functionalities = new Functionality[]{function};

        d_systemUnderTest.execute();

        List<Country> countries = Game.sharedInstance().getD_map().getD_countries();
        assertEquals(2, countries.size(), "Expected 2 countries, found: " + countries.size());
        assertEquals(1, countries.get(0).getD_neighbors().size(), "Expected 1 neighbour for country 1");
        assertEquals(1, countries.get(1).getD_neighbors().size(), "Expected 1 neighbour for country 2");
    }
}