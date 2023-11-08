package main.java.phases;

import main.java.arena.Game;
import main.java.commands.BaseFunctionality;
import main.java.commands.Functionality;
import main.java.models.Continent;
import main.java.models.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.java.arena.Game.sharedInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author kevin on 2023-11-04
 */
public class PostLoadTests {

    /**
     * System under test, i.e. PostLoad phase
     */
    PostLoad d_systemUnderTest;

    /**
     * Create a new connected continent
     */
    @BeforeEach
    void setUp() {
        d_systemUnderTest = new PostLoad();
        Game.sharedInstance().setD_map(null);
    }

    /**
     * Clear the continents list
     */
    @AfterEach
    void tearDown() {
        sharedInstance().getD_map().setD_continents(new ArrayList<>());
        sharedInstance().getD_map().setD_countries(new ArrayList<>());
    }

    @Test
    public void test_givenValidFileNameAsParameter_whenLoadMapCalled_thenMapLoadedSuccessfully() {
        String[] l_params = new String[] {"canada"};

        d_systemUnderTest.loadMap(l_params, null);

        List<Continent> l_continents = Game.sharedInstance().getD_map().getD_continents();
        List<Country> l_countries = Game.sharedInstance().getD_map().getD_countries();

        assertEquals(6, l_continents.size(), "Expected 6 continents but found: " + l_continents.size());
        assertEquals(31, l_countries.size(), "Expected 31 countries but found: " + l_countries.size());
    }

    @Test
    public void test_AddCountryInvalidParams() {
        String[] params = {"-add", "Country1"};
        Functionality functionality = new Functionality(BaseFunctionality.Add, params);
        d_systemUnderTest.editCountry(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry("Country1"));
    }

    @Test
    public void test_RemoveCountry() {
        Game.sharedInstance().getD_map().addCountry("Country1", "Continent1");

        String[] params = {"-remove", "Country1"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, params);
        d_systemUnderTest.editCountry(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry("Country1"));
    }

    @Test
    public void test_RemoveCountryNotFound() {
        String[] params = {"-remove", "Country1"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, params);
        d_systemUnderTest.editCountry(new String[0], new Functionality[]{functionality});
    }

    @Test
    public void test_AddContinentAddInvalidParams() {
        String[] params = {"-add", "Continent1"}; // Invalid format, missing bonus
        Functionality functionality = new Functionality(BaseFunctionality.Add, params);
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getContinent("Continent1"));
    }

    @Test
    public void test_RemoveContinentNotFound() {
        PostLoad postLoad = new PostLoad();

        String[] params = {"-remove", "Continent1"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, params);
        postLoad.editContinent(new String[0], new Functionality[]{functionality});
    }


    @Test
    public void test_EditContinentRemoveNotFound() {
        String[] params = {"-remove", "NonExistentContinent"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, params);
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});
    }

    @Test
    public void test_AddNeighbourInvalidParams() {
        String[] param = {"-add", "country1"}; //invalid format, missing neighbour name
        Functionality functionality = new Functionality(BaseFunctionality.Add, param);
        d_systemUnderTest.editNeighbour(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry(param[1]));
    }
    @Test
    public void test_RemoveNeighbour() {
        String[] params = {"-remove", "country1"};
    }

    @Test
    public void test_RemoveNeighbourNotFound() {

    }
}

