package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * An order object that describes the deployment order for assigning a number of army units to a given country owned by player
 *
 * @author kevin on 2023-10-03
 */
public class DeployOrder implements Order {
    Player d_issuingPlayer;
    Country d_countryForArmyDeployment;
    int d_armyUnitsToDeploy;

    /**
     * Place some armies on one of the current player’s territories
     *
     * @param d_issuingPlayer Player issuing the command
     * @param p_country Country to place the troops
     * @param p_armyUnits Number of troops to deploy
     */
    public DeployOrder(Player d_issuingPlayer, Country p_country, int p_armyUnits) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.d_countryForArmyDeployment = p_country;
        this.d_armyUnitsToDeploy = p_armyUnits;
    }

    @Override
    public void execute() {
        // Simply deploy the army units to the country
        if (d_countryForArmyDeployment != null && d_armyUnitsToDeploy > 0) {
            LogEntryBuffer.getInstance().log("[Deploy Order] " + d_issuingPlayer.getD_name() + " Assigned " + d_armyUnitsToDeploy + " army unit to " + d_countryForArmyDeployment.getD_countryName());
            d_countryForArmyDeployment.addArmyUnits(d_armyUnitsToDeploy);
            LogEntryBuffer.getInstance().log("[Deploy Order] Post deployment to " + d_countryForArmyDeployment.getD_countryName() + " army units stationed :: " + d_countryForArmyDeployment.getD_noOfArmies());
        }
    }
}
