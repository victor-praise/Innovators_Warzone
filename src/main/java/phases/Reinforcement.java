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

    /**
     * At the beginning of every turn a Player is given a number of reinforcement armies, calculated according to the Warzone rules.
     */
    @Override
    public void reinforce() {
        if (TURN_NUMBER > Constants.MAX_TURNS_ALLOWED) {
            System.out.println("No winners in more than " + Constants.MAX_TURNS_ALLOWED + " rounds. This Game is a DRAW");
            for (Player player: Game.sharedInstance().getD_players()) {
                if (player.getD_ownedCountries().isEmpty()) {
                    continue;
                }
                System.out.println(player.getD_name() + " currently owns " + player.getD_ownedCountries().size() + " countries out of " + Game.sharedInstance().getD_map().getD_countries().size() + " countries");
            }
            Game.sharedInstance().setD_gamePhase(new End());
        }
        // Reset available Army units back to total number of armies
        Game.sharedInstance().getD_map().getD_countries().forEach(Country::resetD_availableArmyUnits);

        for (Player player : Game.sharedInstance().getD_players()) {
            if (player.getD_ownedCountries().isEmpty()) {
                continue;
            }
            int l_reinforcementValue = (int) Math.floor((double) player.getD_ownedCountries().size() / 3.0);
            // Check for ownership of continents, which should be added to DEFAULT_REINFORCEMENT
            for (Continent continent : player.getContinentsOwnedByPlayer()) {
                l_reinforcementValue += continent.getD_continentValue();
            }

            player.setD_assignedArmyUnits(Math.max(Constants.DEFAULT_REINFORCEMENT, l_reinforcementValue));

            // Add a random card if the player has conquered any country in previous turn
            if (player.isConqueror()) {
                player.assignRandomCard();
            }
        }
    }

    /**
     * Moves to next state based on state diagram
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Attack());
    }
}
