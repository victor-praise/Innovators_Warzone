package main.java.phases;

import main.java.arena.Game;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * In this phase, the player is requested to issue orders for game play
 *
 * @author kevin on 2023-11-02
 */
public class Attack extends MainPlay {

    Attack() {
        attack();
    }


    /**
     * Requests each player to issue commands in a round-robin fashion
     */
    @Override
    public void attack() {
        System.out.println("--- Attack / Issuing Orders phase ---");

        LogEntryBuffer.getInstance().log("==== Attack / Issuing Orders phase ====" + "\n\n\n");
        boolean didQuitGame = false;
        boolean hasMoreOrders = true;
        while (hasMoreOrders && !didQuitGame) {
            hasMoreOrders = false;
            for (Player l_player : Game.sharedInstance().getD_players()) {
                if (l_player.canIssueOrder()) {
                    hasMoreOrders = true;
                    System.out.println("[GameEngine]: " + l_player.getD_name() + " has currently " + l_player.getD_assignedArmyUnits() + " army units left to deploy");
                    l_player.issue_order();
                }

                if (!Game.Is_Gameplay_On) {
                    System.out.println("Player ordered to end game");
                    didQuitGame = true;
                    break;
                }
            }
        }

        next();
    }

    /**
     * From Attack phase, move to Fortification phase
     */
    @Override
    public void next() {
        Game.sharedInstance().setD_gamePhase(new Fortification());
        System.out.println("--- Moving to Fortification phase --- ");
        LogEntryBuffer.getInstance().log("--- Moving to Fortification phase ---" + "\n");
    }

    /**
     * Displays invalid command message and prints the allowed commands
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

    /**
     * Moves the game to 'End' phase which terminates the game
     */
    @Override
    public void endGame() {
        Game.sharedInstance().setD_gamePhase(new End());
    }
}
