package main.java.commands;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Player;
import main.java.models.SpecialCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for Diplomacy order
 * @author mohammadalshariar on 2023-10-05
 */
public class DiplomacyCommandTest {

    /**
     * Test class for Diplomacy order command
     */
    DiplomacyCommand d_systemUnderTest;

    /**
     * Player issuing the command
     */
    Player d_orderIssuingPlayer;

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

        d_orderIssuingPlayer = new Player("Player 1");
        Player anotherPlayer = new Player("Player 2");
        Game.sharedInstance().setD_players(new ArrayList<Player>(List.of(d_orderIssuingPlayer, anotherPlayer)));
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
     * When player has no Diplomacy card, then Diplomacy order should not be added to player's ordersList
     */
    @Test
    void test_givenDiplomacyCommand_whenNoSpecialCard_thenOrderNotAccepted() {
        // Given
        String[] l_baseParams = new String[]{"Player 2"};
        d_systemUnderTest = new DiplomacyCommand(d_orderIssuingPlayer, l_baseParams);

        //When
        d_systemUnderTest.execute();

        //Then
        assertNull(d_orderIssuingPlayer.nextorder(), "Diplomacy order should have failed");
    }

    /**
     * When player has no Diplomacy card, then Diplomacy order should not be added to player's ordersList
     */
    @Test
    void test_givenDiplomacyCommand_whenPlayerHasSpecialCard_thenOrderAccepted() {
        // Given
        String[] l_baseParams = new String[]{"Player 2"};
        d_orderIssuingPlayer.addSpecialCards(SpecialCard.Diplomacy);
        d_systemUnderTest = new DiplomacyCommand(d_orderIssuingPlayer, l_baseParams);

        //When
        d_systemUnderTest.execute();

        //Then
        assertNotNull(d_orderIssuingPlayer.nextorder(), "Diplomacy order should have failed");
    }

    /**
     * When player has no Diplomacy card, then Diplomacy order should not be added to player's ordersList
     */
    @Test
    void test_givenDiplomacyCommand_whenPlayer2DoesNotExist_thenOrderRejected() {
        // Given
        String[] l_baseParams = new String[]{"Player22"};
        d_orderIssuingPlayer.addSpecialCards(SpecialCard.Diplomacy);
        d_systemUnderTest = new DiplomacyCommand(d_orderIssuingPlayer, l_baseParams);

        //When
        d_systemUnderTest.execute();

        //Then
        assertNull(d_orderIssuingPlayer.nextorder(), "Diplomacy order should have failed");
    }
}
