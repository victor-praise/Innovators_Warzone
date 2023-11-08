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
import static org.junit.jupiter.api.Assertions.*;

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
    public void test_AddCountry() {
        String[] l_params = new String[] {"canada"};

        d_systemUnderTest.loadMap(l_params, null);
        String[] l_param = {"CountryToBeAdded", "Ontario_and_Quebec"};

        Functionality functionality = new Functionality(BaseFunctionality.Add, l_param);
        d_systemUnderTest.editCountry(new String[0], new Functionality[]{functionality});

        assertNotNull(Game.sharedInstance().getD_map().getCountry("CountryToBeAdded"));
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
        Functionality functionality = new Functionality(BaseFunctionality.Remove, new String[]{"Country1"});
        d_systemUnderTest.editCountry(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry("Country1"));
    }

    @Test
    public void test_AddContinent() {
        String[] l_params = new String[] {"canada"};

        d_systemUnderTest.loadMap(l_params, null);

        String[] l_param = {"ContinentToBeAdded", "5"};
        Functionality functionality = new Functionality(BaseFunctionality.Add, l_param);
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});

        assertNotNull(Game.sharedInstance().getD_map().getContinent("ContinentToBeAdded"));
    }

    @Test
    public void test_AddContinentAddInvalidParams() {
        String[] params = {"-add", "Continent1"}; // Invalid format, missing bonus
        Functionality functionality = new Functionality(BaseFunctionality.Add, params);
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getContinent("Continent1"));
    }

    @Test
    public void test_RemoveContinent() {
        PostLoad postLoad = new PostLoad();

        String[] l_params = {"Continent1"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, l_params);
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getContinent("Continent1"));
    }


    @Test
    public void test_RemoveContinentNotFound() {
        Functionality functionality = new Functionality(BaseFunctionality.Remove, new String[]{"continent1"});
        d_systemUnderTest.editContinent(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getContinent("continent1"));
    }

    @Test
    public void test_AddNeighbourInvalidParams() {
        String[] l_param = {"-add", "country1"}; //invalid format, missing neighbour name
        Functionality functionality = new Functionality(BaseFunctionality.Add, l_param);
        d_systemUnderTest.editNeighbour(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry(l_param[1]));
    }
    @Test
    public void test_RemoveNeighbour() {
        String[] l_params = {"-remove", "country1", "neighbour1"};
        Functionality functionality = new Functionality(BaseFunctionality.Remove, l_params);
        d_systemUnderTest.editNeighbour(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry("neighbour1"));
    }

    @Test
    public void test_RemoveNeighbourNotFound() {
        Functionality functionality = new Functionality(BaseFunctionality.Remove, new String[]{"country1", "neighbour1"});
        d_systemUnderTest.editNeighbour(new String[0], new Functionality[]{functionality});

        assertNull(Game.sharedInstance().getD_map().getCountry("neighbour1"));
    }

    @Test
    public void test_AddNeighbour() {
        String[] l_params = new String[] {"canada"};

        d_systemUnderTest.loadMap(l_params, null);

        String[] l_param = {"New_Brunswick", "Quebec-North"};
        Functionality functionality = new Functionality(BaseFunctionality.Add, l_param);
        d_systemUnderTest.editNeighbour(new String[0], new Functionality[]{functionality});

        assertNotNull(Game.sharedInstance().getD_map().getCountry("Quebec-North"));
    }
}