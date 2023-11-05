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
        fortify();
    }

    /**
     *
     */
    @Override
    public void fortify() {
        System.out.println("--- Fortification/Executing Orders Phase ---");
        LogEntryBuffer.getInstance().log("==== Fortification/Executing Orders Phase ====" + "\n\n\n");
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

        // change state before leaving
        next();
    }

    /**
     * Move to the next state
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Reinforcement());
        System.out.println("--- Moving back to Reinforcement phase --- ");
        LogEntryBuffer.getInstance().log("--- Moving back to Reinforcement phase ---" + "\n");
    }
}
