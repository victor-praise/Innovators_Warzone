package main.java.commands;

import main.java.arena.Game;
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
     * @param p_Player player who issued the order
     * @param baseParams parameters given when issuing the order
     */

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
            String l_countryName = d_baseParams[0];
            String l_destinationCountry = d_baseParams[1];
            int l_armyUnits = Integer.parseInt(d_baseParams[2]);
            Map map = Game.sharedInstance().getD_map();
            Country l_ownedSourceCountry = this.d_issuingPlayer.getOwnedCountryWithName((l_countryName));
            Country l_targetDestinationCountry = map.getCountry(l_destinationCountry);

            // check ownership of country
            if(l_ownedSourceCountry == null) {
                l_message = "[AdvanceOrder]: " +d_issuingPlayer.getD_name() + " does not have ownership over country " +l_ownedSourceCountry;
                LogEntryBuffer.getInstance().log(l_message);
                System.out.println(l_message);
                return;
            }

            // check number of units to advance
            if (l_armyUnits < l_ownedSourceCountry.getD_noOfArmies() ) {
                l_message = "[AdvanceOrder]: " + l_ownedSourceCountry.getD_countryName() + " has only " + l_ownedSourceCountry.getD_noOfArmies() + " units of army left. Cannot advance more than " + l_ownedSourceCountry.getD_noOfArmies() + " army units.";
                System.out.println(l_message);
                LogEntryBuffer.getInstance().log(l_message);
                return;
            }

            // check if source and destination countries are adjacent
            if(!l_ownedSourceCountry.hasAdjacentCountry(l_targetDestinationCountry)) {

                l_message = "[AdvanceCommand]: Countries " + l_ownedSourceCountry + " and " + l_destinationCountry + " are not neighbours.";

                System.out.println(l_message);
                LogEntryBuffer.getInstance().log(l_message);
            }

            // Insert Advance Order to players order list
            this.d_issuingPlayer.appendOrderToList(new AdvanceOrder(l_ownedSourceCountry, l_targetDestinationCountry, l_armyUnits));
        } catch (NumberFormatException error) {
            l_message = "[AdvanceOrder]: Advance order requires the third parameter to be an integer, i.e. Units of army to Advance";
            System.out.println(l_message);
            LogEntryBuffer.getInstance().log(l_message);
        }
    }
}