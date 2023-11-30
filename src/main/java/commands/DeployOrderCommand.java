package main.java.commands;

import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.DeployOrder;
import main.java.orders.Order;
import main.java.utils.logger.LogEntryBuffer;

/**
 * Deploys a unit of army to a given country, reduces the available army units
 *
 * @author kevin on 2023-10-03
 */
public class DeployOrderCommand extends PlayerOrderCommand {

    /**
     * Creates a command to deploy order issued by a player
     *
     * @param p_player   player who issued the order
     * @param baseParams parameters supplied when issuing deploy order
     */
    public DeployOrderCommand(Player p_player, String[] baseParams) {
        super(p_player, BaseCommand.Deploy, baseParams, null);
    }

    /**
     * Executes the order provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        String l_message;
        if (d_baseParams == null || d_baseParams.length < 2) {
            l_message = "[DeployOrder]: Deploy order required two parameters. [1] Name of Country to deploy. [2] Units of army to deploy";
            System.out.println(l_message);
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        try {
            String l_countryName = d_baseParams[0];
            int l_armyUnits = Integer.parseInt(d_baseParams[1]);
            Country l_country = this.d_issuingPlayer.getOwnedCountryWithName((l_countryName));

            // check for ownership of country
            if (l_country == null) {
                l_message = "[DeployOrder]: " + d_issuingPlayer.getD_name() + " has no ownership over country with name: " + l_countryName;
                LogEntryBuffer.getInstance().log(l_message);
                return;
            }

            // validate number of army units
            int l_availableUnitsToPlayer = this.d_issuingPlayer.getD_assignedArmyUnits();
            if (l_armyUnits > l_availableUnitsToPlayer) {
                if (l_availableUnitsToPlayer > 0) {
                    l_message = "[DeployOrder]: " + d_issuingPlayer.getD_name() + " has only " + l_availableUnitsToPlayer + " units left. Cannot assign more army than " + l_availableUnitsToPlayer;
                } else {
                    l_message = "[DeployOrder]: " + d_issuingPlayer.getD_name() + " has no more troops to deploy.";
                }
                LogEntryBuffer.getInstance().log(l_message);
                return;
            }

            // Deploy orders will be executed immediately
            Order deployOrder = new DeployOrder(d_issuingPlayer, l_country, l_armyUnits);
            deployOrder.execute();
                // Reduce army units for this player
                this.d_issuingPlayer.reduceArmyUnits(l_armyUnits);
            // Inform about remaining army units
            l_message = "[DeployOrder]: Remaining Units of army to deploy: " + this.d_issuingPlayer.getD_assignedArmyUnits();
            LogEntryBuffer.getInstance().log(l_message);

        } catch (NumberFormatException error) {
            l_message = "[DeployOrder]: Deploy order requires the second parameters to be integer, i.e. Units of army to deploy";
            LogEntryBuffer.getInstance().log(l_message);
        }
    }
}
