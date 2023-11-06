package main.java.phases;

import main.java.arena.Game;
import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.Constants;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-02
 * @author victor
 */
public class Reinforcement extends MainPlay {

    Reinforcement() {
        reinforce();
    }

    /**
     * At the beginning of every turn a Player is given a number of reinforcement armies, calculated according to the Warzone rules.
     */
    @Override
    public void reinforce() {
        // Reset available Army units back to total number of armies
        Game.sharedInstance().getD_map().getD_countries().forEach(Country::resetD_availableArmyUnits);

        for (Player player : Game.sharedInstance().getD_players()) {
            // TODO: Check for ownership of continents, which should be added to DEFAULT_REINFORCEMENT
            int l_reinforcementValue = (int) Math.floor((double) player.getD_ownedCountries().size() / 3.0);
            player.setD_assignedArmyUnits(Math.max(Constants.DEFAULT_REINFORCEMENT, l_reinforcementValue));

            // Add a random card if the player has conquered any country in previous turn
            if (player.isConqueror()) {
                player.assignRandomCard();
            }
        }

        // change state before leaving
        next();
    }

    /**
     * Moves to next state based on state diagram
     */
    @Override
    public void next() {
        LogEntryBuffer.getInstance().log("---- Moving to Attack phase -----" + "\n");
        Game.sharedInstance().setD_gamePhase(new Attack());
    }
}
