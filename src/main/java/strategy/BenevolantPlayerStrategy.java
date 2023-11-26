package main.java.strategy;

import main.java.models.Country;
import main.java.orders.DeployOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Comparator;
import java.util.List;

/**
 * @author kevin on 2023-11-24
 */
public class BenevolantPlayerStrategy extends PlayerStrategy {
    /**
     * Create a new order based on Strategy and adds it to Player's order list
     *
     */
    @Override
    public void createOrder() {
        int availableToDeploy = getPlayer().getD_assignedArmyUnits();
        if (availableToDeploy > 0) {
            // We need to deploy the armies first
            Country toDeploy =  toDeploy();
            DeployOrder deployOrder = new DeployOrder(getPlayer(), toDeploy, 1);
            getPlayer().appendOrderToList(deployOrder);

            // Reduce army units for this player
            getPlayer().reduceArmyUnits(1);
            String l_message = getPlayer().getD_name() + " asked to deploy 1 unit to " + toDeploy.getD_countryName();
            LogEntryBuffer.getInstance().log(l_message);
        } else {
            // Already deployed, we can commit
            getPlayer().setCommitForThisTurn(true);
        }
    }

    /**
     * Identifies the country to attack
     *
     * @return country to which attack should take place
     */
    @Override
    public Country toAttack() {
        return null;
    }

    /**
     * Identifies the country to attack from
     *
     * @return country from where attack should take place
     */
    @Override
    public Country toAttackFrom() {
        return null;
    }

    /**
     * Identifies country to move to
     *
     * @return country to move to
     */
    @Override
    public Country toMoveFrom() {
        return null;
    }

    /**
     * Identifies the country to defend
     *
     * @return country to defend
     */
    @Override
    public Country toDefend() {
        return null;
    }

    /**
     * Identifies the country to deploy to
     * @return country to which deployment should take place
     */
    @Override
    public Country toDeploy() {
        return sortBasedOnNumberOfArmies()[0];
    }

    private Country[] sortBasedOnNumberOfArmies() {
        List<Country> playerOwnedCountries = getPlayer().getD_ownedCountries();
        playerOwnedCountries.sort(new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                return country1.getD_noOfArmies() - country2.getD_noOfArmies();
            }
        });
        return playerOwnedCountries.toArray(new Country[0]);
    }
}
