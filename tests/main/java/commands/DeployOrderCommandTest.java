package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author kevin on 2023-10-04
 */
class DeployOrderCommandTest {

    /**
     * Test class for DeployOrderCommand
     */
    DeployOrderCommand d_systemUnderTest;

    /**
     * Create a new EditContinentCommand
     */
    @BeforeEach
    void setUp() {
        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(2, "China", 1);
        l_asia.addCountry(l_country2);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());
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

    @Test
    void test_givenFixedArmyForPlayer_whenMoreThanAssignedDeployed_thenDeploymentFails() {
        Player l_testPlayer1 = new Player("Player1");
        l_testPlayer1.setD_assignedArmyUnits(1);
        Game.sharedInstance().setD_players(List.of(l_testPlayer1));
        Game.sharedInstance().assignCountriesToPlayers();
        d_systemUnderTest = new DeployOrderCommand(l_testPlayer1, new String[]{"India", "3"});

        d_systemUnderTest.execute();

        assertEquals(1, l_testPlayer1.getD_assignedArmyUnits(), "Expected 1: There were no army units deployed");
    }

    @Test
    void test_givenFixedArmyForPlayer_whenLessThanAssignedDeployed_thenDeploymentSuccess() {
        Player l_testPlayer1 = new Player("Player1");
        l_testPlayer1.setD_assignedArmyUnits(4);
        Game.sharedInstance().setD_players(List.of(l_testPlayer1));
        Game.sharedInstance().assignCountriesToPlayers();
        d_systemUnderTest = new DeployOrderCommand(l_testPlayer1, new String[]{"India", "3"});

        d_systemUnderTest.execute();

        assertEquals(1, l_testPlayer1.getD_assignedArmyUnits(), "3 of 4 should have been deployed and on 1 should be left");
    }
}