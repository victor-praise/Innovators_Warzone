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
        LogEntryBuffer.getInstance().log("==== Attack / Issuing Orders phase ====" + "\n\n\n");
        attack();
    }

    /**
     * Requests each player to issue commands in a round-robin fashion
     */
    @Override
//    public void attack() {
//        boolean didQuitGame = false;
//        boolean hasMoreOrders = true;
//        while (hasMoreOrders && !didQuitGame) {
//            hasMoreOrders = false;
//            for (Player l_player : Game.sharedInstance().getD_players()) {
//                if (l_player.canIssueOrder()) {
//                    hasMoreOrders = true;
//                    if (l_player.canDeployTroops()) {
//                        LogEntryBuffer.getInstance().log("[GameEngine]: " + l_player.getD_name() + " has currently " + l_player.getD_assignedArmyUnits() + " army units left to deploy on " + l_player.getD_ownedCountries().size() + " owned countries");
//                    }
//                    l_player.issue_order();
//                }
//
//                if (!Game.Is_Gameplay_On) {
//                    LogEntryBuffer.getInstance().log("Player" + l_player.getD_name() + "requested to terminate game");
//                    didQuitGame = true;
//                    break;
//                }
//            }
//        }
//    }

    public void attack() {
        boolean l_gameInProgress = true;
        while (l_gameInProgress) {
            l_gameInProgress = executePlayerTurns();
        }
    }
    private boolean executePlayerTurns() {
        boolean l_anyPlayerHasMoreOrders = false;

        for (Player player : Game.sharedInstance().getD_players()) {
            if (player.canIssueOrder()) {
                l_anyPlayerHasMoreOrders = true;
                processPlayerTurn(player);
            }
            if (!Game.Is_Gameplay_On) {
                LogEntryBuffer.getInstance().log("Player " + player.getD_name() + " requested to terminate the game.");
                return false;
            }
        }
        return l_anyPlayerHasMoreOrders;
    }
    private void processPlayerTurn(Player player) {
        if (player.canDeployTroops()) {
            LogEntryBuffer.getInstance().log("[GameEngine]: " + player.getD_name() + " has currently " +
                    player.getD_assignedArmyUnits() + " army units left to deploy on " +
                    player.getD_ownedCountries().size() + " owned countries");
        }

        player.issue_order();
    }

    /**
     * From Attack phase, move to Fortification phase
     */
    @Override
    public void next() {
        setValidCommand(true);
        LogEntryBuffer.getInstance().log("--- Moving to Fortification phase ---" + "\n");
        Game.sharedInstance().setD_gamePhase(new Fortification());
    }

    /**
     * Displays invalid command message and prints the allowed commands
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        displayValidCommands();
    }

    /**
     * Display All Valid Commands in this State
     */
    private void displayValidCommands() {
        LogEntryBuffer.getInstance().log("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        LogEntryBuffer.getInstance().log("1. deploy countryID numarmies");
        LogEntryBuffer.getInstance().log("2. advance countrynamefrom countynameto numarmies");
        LogEntryBuffer.getInstance().log("3. bomb countryID");
        LogEntryBuffer.getInstance().log("4. blockade countryID");
        LogEntryBuffer.getInstance().log("5. airlift sourcecountryID targetcountryID numarmies");
        LogEntryBuffer.getInstance().log("6. negotiate playerID");
        LogEntryBuffer.getInstance().log("quit/commit");
        LogEntryBuffer.getInstance().log(" --- ");
    }
}
