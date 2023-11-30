package main.java.commands;

import main.java.models.Country;
import main.java.models.Player;
import main.java.models.SpecialCard;
import main.java.orders.BlockadeOrder;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-05
 */
public class BlockadeCommand extends PlayerOrderCommand {

    /**
     * Creates a new Command object to blockade current player's one territory and mark it as NEUTRAL
     *
     * @param p_player   player who issued the order
     * @param baseParams represents any parameter to the baseCommand
     */
    public BlockadeCommand(Player p_player, String[] baseParams) {
        super(p_player, BaseCommand.Blockade, baseParams, null);
    }

    /**
     * Validates the Blockade order issued by the player, and if valid, inserts into the players order queue
     */
    @Override
    public void execute() {
        String l_message;
        if (isDeploymentPending()) {
            l_message = "[Blockade]: Blockade order requires all deployment to be completed";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        if (!this.d_issuingPlayer.hasSpecialCard(SpecialCard.Blockade)) {
            l_message = "[Blockade]: Player " + this.d_issuingPlayer.getD_name() + " does not have an Blockade card";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        if (d_baseParams == null || d_baseParams.length < 1) {
            l_message = "[Blockade]: Blockade order requires one parameters. [1] Name of Country to convert to Neutral.";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        String l_countryName = d_baseParams[0];
        Country l_sourceCountry = this.d_issuingPlayer.getOwnedCountryWithName((l_countryName));
        if (l_sourceCountry == null) {
            l_message = "[Blockade]: " + d_issuingPlayer.getD_name() + " has no ownership over country with name: " + l_countryName;
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        // Insert the blockade-order to players order list
        this.d_issuingPlayer.appendOrderToList(new BlockadeOrder(d_issuingPlayer, l_sourceCountry));
        this.d_issuingPlayer.removeSpecialCard(SpecialCard.Blockade);
    }
}
