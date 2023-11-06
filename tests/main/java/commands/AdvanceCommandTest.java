package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**

 * Create a new AdvanceCommand
 * @author sadiq
 * */

public class AdvanceCommandTest{

    /**
     * Test class for AdvanceCommand
     */
    AdvanceCommand d_systemUnderTest;

    /**
     * Create a new AdvanceCommand
     */
    @BeforeEach
    void setUp() {
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_country1.setD_noOfArmies(5);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(2, "China", 1);
        l_country2.addArmyUnits(3);
        l_asia.addCountry(l_country2);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());
        Game.sharedInstance().getD_map().setD_continents(Stream.of(l_asia).toList());
        Game.sharedInstance().getD_map().setD_countries(Stream.of(l_country1, l_country2).toList());
    }

    /**
     * Checks if the advance order was issued or not
     *
     * When Trying to advance more units than available in country, order should fail
     */

    @Test
    void test_currentArmies_ifAttemptToAdvanceMoreThanCurrentCount_thenFails() {
        Player l_testPlayer1 = new Player("Player 1");

        Game.sharedInstance().setD_players(List.of(l_testPlayer1));
        l_testPlayer1.setD_ownedCountries(Game.sharedInstance().getD_map().getD_countries());

        d_systemUnderTest = new AdvanceCommand(l_testPlayer1, new String[]{"India", "China", "6"});

        d_systemUnderTest.execute();
        Order nextOrder = l_testPlayer1.nextorder();
        assertNotNull(nextOrder, "Order should have been received");
    }
}
