package main.java.phases;

import main.java.arena.Game;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-02
 */
public class Attack extends MainPlay {

    Attack() {
        attack();
    }

    /**
     *
     */
    @Override
    public void attack() {
        System.out.println("--- Attack / Issuing Orders phase ---");
        LogEntryBuffer.getInstance().log("==== Attack / Issuing Orders phase ====" + "\n\n\n");
        boolean hasMoreOrders = true;
        while (hasMoreOrders) {
            hasMoreOrders = false;
            for (Player l_player: Game.sharedInstance().getD_players()) {
                if (l_player.canIssueOrder()) {
                    hasMoreOrders = true;
                    System.out.println("[GameEngine]: " + l_player.getD_name() + " has currently " + l_player.getD_assignedArmyUnits() + " army units left to deploy");
                    l_player.issue_order();
                }
            }
        }

        // change state before leaving
        next();
    }

    /**
     *
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Fortification());
        System.out.println("--- Moving to Fortification phase --- ");
        LogEntryBuffer.getInstance().log("--- Moving to Fortification phase ---" + "\n");
    }

    /**
     *
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        System.out.println("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        System.out.println("1. deploy countryID numarmies");
        System.out.println("2. advance countrynamefrom countynameto numarmies");
        System.out.println("3. bomb countryID");
        System.out.println("4. blockade countryID");
        System.out.println("5. airlift sourcecountryID targetcountryID numarmies");
        System.out.println("6. negotiate playerID");
        System.out.println(" --- ");
    }
}
