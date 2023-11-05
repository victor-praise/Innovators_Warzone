package main.java.commands;

import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.AirliftOrder;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-11-04
 */
public class AirliftCommand extends PlayerOrderCommand {

    /**
     * Creates a new Command object to airlift armies from one territory to another
     *
     * @param p_player        player who issued the order
     * @param baseParams      represents any parameter to the baseCommand
     */
    public AirliftCommand(Player p_player, String[] baseParams) {
        super(p_player, BaseCommand.Airlift, baseParams, null);
    }

    /**
     * Validates the Airlift order issued by the player, and if valid, inserts into the players order queue
     */
    @Override
    public void execute() {
        String l_message;
        if (d_baseParams == null || d_baseParams.length < 3) {
            l_message = "[Airlift]: Airlift order required three parameters. [1] Name of Country to lift from. [2] Name of Country to drop to [3] Units of army to airlift";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        String l_sourceCountryName = d_baseParams[0];
        Country l_sourceCountry = this.d_issuingPlayer.getOwnedCountryWithName((l_sourceCountryName));
        if (l_sourceCountry == null) {
            l_message = "[Airlift]: " + d_issuingPlayer.getD_name() + " has no ownership over country with name: " + l_sourceCountryName;
            LogEntryBuffer.getInstance().log(l_message);
            System.out.println(l_message);
            return;
        }

        String l_targetCountryName = d_baseParams[1];
        Country l_targetCountry = this.d_issuingPlayer.getOwnedCountryWithName((l_targetCountryName));
        if (l_targetCountry == null) {
            l_message = "[Airlift]: " + d_issuingPlayer.getD_name() + " has no ownership over country with name: " + l_targetCountryName;
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        int l_armyUnitsToAirlift = 0;
        try {
            l_armyUnitsToAirlift = Integer.parseInt(d_baseParams[2]);

            if (l_armyUnitsToAirlift > l_sourceCountry.getD_noOfArmies()) {
                l_message = "[Airlift]: " + d_issuingPlayer.getD_name() + " can not airlift more than available units of: " + l_sourceCountry.getD_noOfArmies();
                LogEntryBuffer.getInstance().log(l_message);
                return;
            }
        } catch (NumberFormatException exception) {
            l_message = "[Airlift]: order requires the third parameters to be integer, i.e. Units of army to deploy. Incorrect value: " + d_baseParams[2];
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        // Insert the airlift-order to players order list
        this.d_issuingPlayer.appendOrderToList(new AirliftOrder(l_sourceCountry, l_targetCountry, l_armyUnitsToAirlift));
    }
}
