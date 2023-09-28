package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for EditContinentCommand
 * @author kevin on 2023-09-26
 */
class EditContinentCommandTest {
    /**
     * System under test, i.e. EditContinentCommand
     */
    EditContinentCommand d_systemUnderTest;

    /**
     * Create a new EditContinentCommand
     */
    @BeforeEach
    void setUp() {
        d_systemUnderTest = new EditContinentCommand(null, null);
    }

    /**
     * Clear the continents list
     */
    @AfterEach
    void tearDown() {
        Game.sharedInstance().getD_map().setD_continents(new ArrayList<>());
    }

    /**
     * Given an add functionality of EditContinent command, when executed, a continent is added
     */
    @Test
    void testGivenAddFunctionality_whenCommandExecuted_thenContinentCreated() {
        Functionality function = new Functionality(BaseFunctionality.Add, new String[]{"Asia", "3"});
        d_systemUnderTest.functionalities = new Functionality[]{function};

        d_systemUnderTest.execute();

        int continentCount = Game.sharedInstance().getD_map().getD_continents().size();
        assertEquals(continentCount, 1, "Expected 1 found " + continentCount);
    }

    /**
     * Given an add functionality of EditContinent command, when same name continent added again, addition is rejected
     */
    @Test
    void testGivenAddFunctionality_whenContinentExists_thenAdditionRejected() {
        Functionality function = new Functionality(BaseFunctionality.Add, new String[]{"Asia", "3"});
        d_systemUnderTest.functionalities = new Functionality[]{function};
        d_systemUnderTest.execute();

        // Repeat 1 more time, this one should be rejected
        d_systemUnderTest.execute();

        int continentCount = Game.sharedInstance().getD_map().getD_continents().size();
        assertEquals(continentCount, 1, "Expected 1 found " + continentCount);
    }

    /**
     * Given a remove functionality of EditContinent command, when executed, then continent removed
     */
    @Test
    void test_givenRemoveFunctionality_whenExecuted_thenContinentRemoved() {
        Continent l_asia = new Continent("Asia", 2);
        List<Continent> l_continents = new ArrayList<Continent>(List.of(l_asia));
        Game.sharedInstance().getD_map().setD_continents(l_continents);

        int continentCount = Game.sharedInstance().getD_map().getD_continents().size();
        assertEquals(1, continentCount);

        Functionality function = new Functionality(BaseFunctionality.Remove, new String[]{"Asia"});
        d_systemUnderTest.functionalities = new Functionality[]{function};

        d_systemUnderTest.execute();

        continentCount = Game.sharedInstance().getD_map().getD_continents().size();
        assertEquals(continentCount, 0, "Expected 0 found " + continentCount);
    }
}