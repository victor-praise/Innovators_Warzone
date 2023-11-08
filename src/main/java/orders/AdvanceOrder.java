package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author sadiqali
 * Advances a certain number of army units from player-owned source country to destination. Destination country may or may not be owned by another player.
 */
public class AdvanceOrder implements Order {
    Player d_issuingPlayer;
    Country d_sourceCountry;
    Country d_destinationCountry;
    int d_armyUnitsToAdvance;

    /**
     * Move some armies from one of the current player’s territories (source) to an adjacent territory
     *
     * @param p_issuingPlayer Player issuing the command
     * @param p_sourceCountry Country to move the troops from
     * @param p_destinationCountry Country to move the troops to
     * @param p_armyUnits Units of troops to advance
     */
    public AdvanceOrder(Player p_issuingPlayer, Country p_sourceCountry, Country p_destinationCountry, int p_armyUnits) {
        this.d_issuingPlayer = p_issuingPlayer;
        this.d_sourceCountry = p_sourceCountry;
        this.d_destinationCountry = p_destinationCountry;
        this.d_armyUnitsToAdvance = p_armyUnits;
    }

    /**
     * execute Advance order
     */
    @Override
    public void execute() {
        Player l_destinationOwner = d_destinationCountry.getD_ownedBy();

        // If there were some deaths based on previous attacks, only consider current army count
        d_armyUnitsToAdvance = Math.min(d_armyUnitsToAdvance, d_sourceCountry.getD_noOfArmies());

        d_sourceCountry.reduceArmyUnits(d_armyUnitsToAdvance);
        if (l_destinationOwner.equals(d_issuingPlayer)) {
            // Same Owner, just transfer
            d_destinationCountry.addArmyUnits(d_armyUnitsToAdvance);
            String l_message = d_issuingPlayer.getD_name() + "Transferred " + d_armyUnitsToAdvance + " from " + d_sourceCountry.getD_countryName() + " to " + d_destinationCountry.getD_countryName();
            LogEntryBuffer.getInstance().log(l_message);
        } else {
            // Attack ensues
            long defendingArmyUnits = d_destinationCountry.getD_noOfArmies();
            long attackerCanKill = Math.round(Math.floor(d_armyUnitsToAdvance * 0.6));
            long defenderCanKill = Math.round(Math.floor(defendingArmyUnits * 0.7));

            long attackingArmyLeft = Math.max(0, d_armyUnitsToAdvance - defenderCanKill);
            long defendingArmyLeft = Math.max(0, defendingArmyUnits - attackerCanKill);
            String l_message;
            if (attackingArmyLeft >= defendingArmyLeft) {
                // Attacker won
                l_message = d_issuingPlayer.getD_name() + "Attacked with " + d_armyUnitsToAdvance + " and eliminated " + defendingArmyUnits + " army units in " + d_destinationCountry.getD_countryName();
                LogEntryBuffer.getInstance().log(l_message);

                d_destinationCountry.setD_noOfArmies((int)attackingArmyLeft);
                d_issuingPlayer.receiveOwnershipForCountry(d_destinationCountry);
                d_issuingPlayer.setConqueror(true);
            } else {
                // Defender Won
                l_message = l_destinationOwner.getD_name() + " Successfully defended against " + d_armyUnitsToAdvance + " and is left with " + defendingArmyLeft + " army units in " + d_destinationCountry.getD_countryName();
                LogEntryBuffer.getInstance().log(l_message);

                d_destinationCountry.setD_noOfArmies((int)defendingArmyLeft);
            }
        }
    }
}
