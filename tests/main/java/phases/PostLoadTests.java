package main.java.phases;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.java.arena.Game.sharedInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
