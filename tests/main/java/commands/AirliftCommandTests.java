package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for the Airlift command
 * @author kevin on 2023-11-05
 */
public class AirliftCommandTests {

    /**
     * Test class for AirliftCommand
     */
    AirliftCommand d_systemUnderTest;

    Player d_orderIssuingPlayer;

    /**
     * Create a new AirliftCommand
     */
    @BeforeEach
    void setUp() {
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(2, "China", 1);
        l_asia.addCountry(l_country2);
        Country l_country3 = new Country(3, "Japan", 1);
        l_asia.addCountry(l_country3);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());
        l_country2.addNeighbour(l_country3.getD_countryID());
        Game.sharedInstance().getD_map().setD_continents(Stream.of(l_asia).toList());
        Game.sharedInstance().getD_map().setD_countries(Stream.of(l_country1, l_country2, l_country3).toList());

        d_orderIssuingPlayer = new Player("Player1");
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
     * When an airlift order is given to a country not owned by issuing player, then order is not accepted
     */
    @Test
    void test_givenAirliftCommand_whenNoOwnershipOfTargetCountry_thenOrderNotAccepted() {
        //Given
        String[] l_baseparams = new String[]{"India", "Pakistan", "5"};
        d_systemUnderTest = new AirliftCommand(d_orderIssuingPlayer, l_baseparams);

        //When
        d_systemUnderTest.execute();

        //Then
        assertNull(d_orderIssuingPlayer.nextorder(), "Airlift order should have failed");
    }

    /**
     * When an airlift order is valid, then order is accepted
     */
    @Test
    void test_givenValidAirliftCommand_whenExecuted_thenOrderAccepted() {
        //Given
        List<Country> l_allCountries = Game.sharedInstance().getD_map().getD_countries();
        l_allCountries.forEach(country -> country.setD_noOfArmies(10));
        d_orderIssuingPlayer.setD_ownedCountries(l_allCountries);
        String[] l_baseParams = new String[]{"India", "Japan", "5"};
        d_systemUnderTest = new AirliftCommand(d_orderIssuingPlayer, l_baseParams);

        //When
        d_systemUnderTest.execute();

        //Then
        Order l_order = d_orderIssuingPlayer.nextorder();
        assertNotNull(l_order, "Next Order should have been Airlift order");
    }
}
