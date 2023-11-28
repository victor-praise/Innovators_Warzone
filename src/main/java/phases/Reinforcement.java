package main.java.phases;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.Constants;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-02
 * @author victor
 */
public class Reinforcement extends MainPlay {

    /**
     * Determines the number of turn in this game
     */
    public static long TURN_NUMBER = 1;

    Reinforcement() {
        LogEntryBuffer.getInstance().log("==== Reinforcement phase {Turn Number :: " + TURN_NUMBER++ + "} ====" + "\n");
        reinforce();
    }

    @Override
    public void reinforce() {
        resetAvailableArmyUnits();

        for (Player player : Game.sharedInstance().getD_players()) {
            int reinforcementValue = calculateReinforcementValue(player);
            player.setD_assignedArmyUnits(Math.max(Constants.DEFAULT_REINFORCEMENT, reinforcementValue));

            // Add a random card if the player has conquered any country in the previous turn
            if (player.isConqueror()) {
                player.assignRandomCard();
            }
        }

        // Change state before leaving
        next();
    }

    /**
     * Resets the available army units for all countries on the game map.
     */
    private void resetAvailableArmyUnits() {
        Game.sharedInstance().getD_map().getD_countries().forEach(Country::resetD_availableArmyUnits);
    }

    /**
     * Calculates the reinforcement value for a player based on owned countries and continents.
     *
     * @param player The player for whom to calculate the reinforcement value.
     * @return The calculated reinforcement value.
     */
    private int calculateReinforcementValue(Player player) {
        int l_reinforcementValue = (int) Math.floor((double) player.getD_ownedCountries().size() / 3.0);

        // Check for ownership of continents, which should be added to DEFAULT_REINFORCEMENT
        for (Continent continent : player.getContinentsOwnedByPlayer()) {
            l_reinforcementValue += continent.getD_continentValue();
        }

        return l_reinforcementValue;
    }

    /**
     * Moves to next state based on state diagram
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Attack());
    }
}
