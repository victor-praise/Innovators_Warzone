package main.java.commands;

import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.AdvanceOrder;
import main.java.utils.logger.LogEntryBuffer;
import main.java.models.Map;

/**
 * Advances certain number of army units from source owned country to a target country which may or may not be owned by another player.
 * @author sadiqali
 */

public class AdvanceCommand extends PlayerOrderCommand{
    /**
     * 
     * @param p_Player player who issued the order
     * @param baseParams parameters given when issuing the order
     */

    Map m = new Map();

    public AdvanceCommand(Player p_Player, String[] baseParams) {
        super(p_Player, BaseCommand.AdvanceCommand, baseParams, null);
    }

    @Override
    public void execute() {
        String l_message;
        if (d_baseParams == null || d_baseParams.length < 3) {
            l_message = "[AdvanceOrder]: Advance order requires four parameters. [1] Source country from which armies are to be advanced. [2] Destination country to which armies are to be advanced. [3] Number of units of army to be advanced.";
            System.out.println(l_message);
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        try {
            String l_sourceCountry = d_baseParams[0];
            String l_destinationCountry = d_baseParams[1];
            int l_armyUnits = Integer.parseInt(d_baseParams[2]);
            Country l_ownedSourceCountry = this.d_issuingPlayer.getOwnedCountryWithName(l_sourceCountry);
            Country l_targetDestinationCountry = m.getCountry(l_destinationCountry);


            // check ownership of country
            if(l_ownedSourceCountry == null) {
                l_message = "[AdvanceOrder]: " +d_issuingPlayer.getD_name() + " does not have ownership over country " +l_sourceCountry;
                LogEntryBuffer.getInstance().log(l_message);
                System.out.println(l_message);
                return;
            }

            // check number of units to advance
            int l_availableUnitsToPlayer = this.d_issuingPlayer.getD_assignedArmyUnits();
            if (l_armyUnits > l_availableUnitsToPlayer) {
                l_message = "[AdvanceOrder]: " + d_issuingPlayer.getD_name() + "has only" + l_availableUnitsToPlayer + " units of army left. Cannot assign more than " + l_availableUnitsToPlayer;
                System.out.println(l_message);
                LogEntryBuffer.getInstance().log(l_message);
                return;
            }

            // Insert Advance Order to players order list
            this.d_issuingPlayer.appendOrderToList(new AdvanceOrder(l_ownedSourceCountry, l_targetDestinationCountry, l_armyUnits));
            // Reduce the army units for the player
            this.d_issuingPlayer.reduceArmyUnits(l_armyUnits);
            // Return the remaining army units
            l_message = "[DeployOrder]: Remaining Units of army which can be advanced: " + this.d_issuingPlayer.getD_assignedArmyUnits();
            System.out.println(l_message);
            LogEntryBuffer.getInstance().log(l_message);
        
        } catch (NumberFormatException error) {
            l_message = "[AdvanceOrder]: Advance order requires the third parameter to be an integer, i.e. Units of army to Advance";
            System.out.println(l_message);
            LogEntryBuffer.getInstance().log(l_message);
        }
    }
}
