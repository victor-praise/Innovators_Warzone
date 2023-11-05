package main.java.phases;

import main.java.arena.Game;
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
        for (Player player : Game.sharedInstance().getD_players()) {
            // TODO: Check for ownership of continents, which should be added to DEFAULT_REINFORCEMENT
            player.setD_assignedArmyUnits(Constants.DEFAULT_REINFORCEMENT);

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
        Game.sharedInstance().setD_gamePhase(new Attack());
        System.out.println("--- Moving to Attack phase --- ");
        LogEntryBuffer.getInstance().log("---- Moving to Attack phase -----" + "\n");
    }
}
