package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * Advance some armies from one of the current player’s territories to any another territory
 *
 * @author kevin on 2023-11-04
 */
public class AirliftOrder implements Order {

    Player d_issuingPlayer;
    Country d_sourceCountry;
    Country d_targetCountry;
    int d_armyUnitsToAirlift;

    /**
     * Transfer some armies from one of the current player’s territories to any another territory
     *
     * @param d_issuingPlayer Player issuing the command
     * @param p_sourceCountry Country to move the troops from
     * @param p_targetCountry Country to move the troops to
     * @param p_numOfArmies   Units of Troops to Airlift
     */
    public AirliftOrder(Player d_issuingPlayer, Country p_sourceCountry, Country p_targetCountry, int p_numOfArmies) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_armyUnitsToAirlift = p_numOfArmies;
    }

//    /**
//     * Each order will have an execute method where the execution logic will be inserted
//     */
//    @Override
//    public void execute() {
//
//        // If there were some deaths based on previous attacks, only consider current army count
//        d_armyUnitsToAirlift = Math.min(d_armyUnitsToAirlift, d_sourceCountry.getD_noOfArmies());
//
//        d_sourceCountry.reduceArmyUnits(this.d_armyUnitsToAirlift);
//        d_targetCountry.addArmyUnits(this.d_armyUnitsToAirlift);
//
//        String l_message = "[Airlift Order] Airlifted  " + d_armyUnitsToAirlift + " army units from " + d_sourceCountry.getD_countryName() + " to " + d_targetCountry.getD_countryName();
//        LogEntryBuffer.getInstance().log(l_message);
//    }
//}

    /**
     * Executes an airlift order by moving army units from the source country to the target country.
     * If there were casualties in previous attacks, only the remaining army units are considered.
     */
    @Override
    public void execute() {
        int l_actualAirliftedUnits = Math.min(d_armyUnitsToAirlift, d_sourceCountry.getD_noOfArmies());

        reduceArmiesInSourceCountry(l_actualAirliftedUnits);
        addArmiesToTargetCountry(l_actualAirliftedUnits);

        logAirliftMessage(l_actualAirliftedUnits);
    }

    /**
     * Reduces the number of army units in the source country by the specified amount.
     *
     * @param p_airliftedUnits The number of army units to be airlifted.
     */
    private void reduceArmiesInSourceCountry(int p_airliftedUnits) {
        d_sourceCountry.reduceArmyUnits(p_airliftedUnits);
    }

    /**
     * Adds the specified number of army units to the target country.
     *
     * @param p_airliftedUnits The number of army units to be airlifted.
     */
    private void addArmiesToTargetCountry(int p_airliftedUnits) {
        d_targetCountry.addArmyUnits(p_airliftedUnits);
    }

    /**
     * Logs a message indicating the execution of the airlift order.
     *
     * @param p_airliftedUnits The actual number of army units airlifted.
     */
    private void logAirliftMessage(int p_airliftedUnits) {
        String message = "[Airlift Order] Airlifted " + p_airliftedUnits +
                " army units from " + d_sourceCountry.getD_countryName() +
                " to " + d_targetCountry.getD_countryName();
        LogEntryBuffer.getInstance().log(message);
    }
}
