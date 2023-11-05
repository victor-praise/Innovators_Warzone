package main.java.orders;

import main.java.models.Country;
import main.java.utils.logger.LogEntryBuffer;

/**
 * Advance some armies from one of the current player’s territories to any another territory
 *
 * @author kevin on 2023-11-04
 */
public class AirliftOrder implements Order {

    Country d_sourceCountry;
    Country d_targetCountry;
    int d_numberOfArmies;

    public AirliftOrder(Country p_sourceCountry, Country p_targetCountry, int p_numOfArmies) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_numberOfArmies = p_numOfArmies;
    }

    /**
     * Each order will have an execute method where the execution logic will be inserted
     */
    @Override
    public void execute() {
        d_sourceCountry.reduceArmyUnits(this.d_numberOfArmies);
        d_targetCountry.addArmyUnits(this.d_numberOfArmies);

        String l_message = "[Airlift Order] Airlifted  " + d_numberOfArmies + " army units from " + d_sourceCountry.getD_countryName() + " to " + d_targetCountry.getD_countryName();
        LogEntryBuffer.getInstance().log(l_message);
        System.out.println(l_message);
    }
}
