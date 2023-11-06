package main.java.commands;

import main.java.arena.Game;
import main.java.models.Player;
import main.java.orders.DiplomacyOrder;
import main.java.utils.logger.LogEntryBuffer;

/**
 * Diplomacy command for executing negotiation
 * @author mohammadalshariar
 */
public class DiplomacyCommand extends PlayerOrderCommand {
    /**
     * Creates a new Diplomacy command for a player
     *
     * @param p_player who initiate negotiation
     * @param baseParams  represents any parameter to the base command
     */
    public DiplomacyCommand(Player p_player, String[] baseParams) {
        super(p_player, BaseCommand.Diplomacy, baseParams,null);
    }

    /**
     * Executes diplomacy command to negotiate between player
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        String l_message;
        if (d_baseParams == null || d_baseParams.length < 1) {
            l_message = "[DiplomacyOrder]: Diplomacy order requires one parameters. [1] Name of Player.";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        String l_playerName = d_baseParams[0];
        Player l_player = Game.sharedInstance().getPlayer(l_playerName);

        // check player exists
        if (l_player == null) {
            l_message = "[DiplomacyOrder]: " + l_playerName + " could not be found.";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        // Insert the diplomacy order to players order list
        this.d_issuingPlayer.appendOrderToList(new DiplomacyOrder(this.d_issuingPlayer, l_player));
        // Inform about remaining army units
        l_message = "[DiplomacyOrder]:" + this.d_issuingPlayer.getD_name() + " and" + l_playerName + "have negotiated each other.";
        LogEntryBuffer.getInstance().log(l_message);
    }
}
