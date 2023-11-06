package main.java.commands;

import main.java.models.Country;
import main.java.models.Player;
import main.java.models.SpecialCard;
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
        if (isDeploymentPending()) {
            l_message = "[AirliftOrder]: Airlift order requires all deployment to be completed";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        if (d_baseParams == null || d_baseParams.length < 3) {
            l_message = "[Airlift]: Airlift order required three parameters. [1] Name of Country to lift from. [2] Name of Country to drop to [3] Units of army to airlift";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        if (!this.d_issuingPlayer.hasSpecialCard(SpecialCard.Airlift)) {
            l_message = "[Airlift]: Player " + this.d_issuingPlayer.getD_name() + " does not have an Airlift card";
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
        int l_availableUnits = l_sourceCountry.getD_availableArmyUnits();
        try {
            l_armyUnitsToAirlift = Integer.parseInt(d_baseParams[2]);

            // check number of units to advance
            if (l_armyUnitsToAirlift > l_availableUnits) {
                l_message = "[Airlift]: " + d_issuingPlayer.getD_name() + " can not airlift more than available units of: " + l_availableUnits;
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
        this.d_issuingPlayer.removeSpecialCard(SpecialCard.Airlift);

        // Reduce army units for source country, to prevent Player from airlifting more than available units
        l_availableUnits -= l_armyUnitsToAirlift;
        l_sourceCountry.setD_availableArmyUnits(l_availableUnits);
    }
}
