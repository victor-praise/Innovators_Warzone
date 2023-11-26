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
     * Execute orders in round-robin fashion
     */
    @Override
    public void fortify() {
        boolean hasMoreOrders = true;
        while (hasMoreOrders) {
            hasMoreOrders = false;
            for (Player l_player : Game.sharedInstance().getD_players()) {
                Order l_order = l_player.nextorder();
                if (l_order != null) {
                    l_order.execute();
                    hasMoreOrders = true;
                }
            }
        }

        // Remove commit flags from all players
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
