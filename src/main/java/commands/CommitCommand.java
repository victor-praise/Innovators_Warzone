package main.java.commands;

import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-05
 */
public class CommitCommand extends PlayerOrderCommand {
    /**
     * Creates a new Commit Order command for a given player
     */
    public CommitCommand(Player p_player) {
        super(p_player, BaseCommand.Commit, null, null);
    }

    /**
     * Commits all the orders for this turn
     */
    public void execute() {
        if (d_issuingPlayer.getD_assignedArmyUnits() > 0) {
            LogEntryBuffer.getInstance().log("You have forfeited " + d_issuingPlayer.getD_assignedArmyUnits() + " reinforcement units");
        }
        d_issuingPlayer.setCommitForThisTurn(true);
    }
}
