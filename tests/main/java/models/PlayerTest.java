package main.java.models;

import main.java.arena.Game;
import main.java.strategy.HumanPlayerStrategy;
import main.java.strategy.PlayerStrategy;
import main.java.strategy.Strategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author kevin on 2023-11-04
 */
public class PlayerTest {

    /**
     * System under test, i.e. Map
     */
    Player d_systemUnderTest;

    /**
     * Create a new connected continent
     */
    @BeforeEach
    void setUp() {
        PlayerStrategy defaultStrategy = new HumanPlayerStrategy(Strategy.Human);
        d_systemUnderTest = new Player("Player1", defaultStrategy);
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
    public void test_givenAPlayer_whenAssignedASpecialCard_thenSpecialCardAddedToList() {
        // when
        d_systemUnderTest.assignRandomCard();

        // then
        assertEquals(1, d_systemUnderTest.getSpecialCards().size(), "Should have 1 specail card after assignment");
    }

    @Test
    public void test_givenASpecialCard_whenAssignedToPlayer_thenSpecialCardAvailable() {
        // Given
        SpecialCard specialCard = SpecialCard.Bomb;

        // when
        d_systemUnderTest.addSpecialCards(specialCard);

        // then
        List<SpecialCard> playerCards = d_systemUnderTest.getSpecialCards();
        assertEquals(1, playerCards.size(), "Should have 1 specail card after assignment");
        assertEquals(specialCard, playerCards.get(0));
        assertTrue(d_systemUnderTest.hasSpecialCard(SpecialCard.Bomb));
        assertTrue(d_systemUnderTest.hasSpecialCardOfType("bomb"));

    }

    @Test
    public void testGetContinentsOwnedByPlayerWithNoContinents() {
        // Given
        List<Continent> ownedContinents = d_systemUnderTest.getContinentsOwnedByPlayer();

        // Then
        assertNotNull(ownedContinents);
        assertTrue(ownedContinents.isEmpty(), "Owned continents should be empty when no continents exist.");
    }
}
