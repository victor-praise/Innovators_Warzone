package main.java.phases;

import main.java.arena.Game;
import main.java.models.Player;
import main.java.orders.Order;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-02
 */
public class Fortification extends MainPlay {

    Fortification() {
        LogEntryBuffer.getInstance().log("==== Fortification/Executing Orders Phase ====" + "\n\n\n");
        fortify();
    }

    /**
     * Executes fortification orders for all players until no more orders are available.
     * Resets commit flags for all players and changes the game state before leaving.
     */
    public void fortify() {
        while (executeNextOrder()) {
            // Continue processing orders until there are no more
        }

        // Reset commit flags for all players
        resetCommitFlags();

        // Change state before leaving
        next();
    }

    /**
     * Executes the next order for each player, if available.
     *
     * @return {@code true} if there are more orders to execute, {@code false} otherwise.
     */
    private boolean executeNextOrder() {
        for (Player player : Game.sharedInstance().getD_players()) {
            Order order = player.nextorder();
            if (order != null) {
                order.execute();
                return true; // There is at least one more order to execute
            }
        }
        return false; // No more orders to execute
    }

    /**
     * Resets commit flags for all players.
     */
    private void resetCommitFlags() {
        Game.sharedInstance().getD_players().forEach(player -> player.setCommitForThisTurn(false));
    }

    public void checkIfWeHaveWinner() {
        Player winner = Game.sharedInstance().getD_map().playerOwningAllCountries();
        if (winner != null) {
            // We have found our winner
            LogEntryBuffer.getInstance().log("\n****** " + winner.getD_name() + " has won the game in "  + Reinforcement.TURN_NUMBER + " turns. ****** !!!\n\n");
            Game.sharedInstance().setD_gamePhase(new End());
        }
    }

    /**
     * Move to the next state
     */
    @Override
    public void next() {
        checkIfWeHaveWinner();
        Game.sharedInstance().setD_gamePhase(new Reinforcement());
    }
}
