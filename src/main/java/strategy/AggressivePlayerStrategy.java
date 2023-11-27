package main.java.strategy;

import main.java.models.Country;
import main.java.orders.AdvanceOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Random;

/**
 * @author kevin on 2023-11-24
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

    /**
     * Create a new order based on Strategy and adds it to Player's order list
     *
     */
    @Override
    public void createOrder() {

        if (getPlayer().getD_assignedArmyUnits() > 0) {
            // we need to deploy
            int deploymentUnits = getPlayer().getD_assignedArmyUnits();
            deployArmyUnits(deploymentUnits);
        } else {
            // Can commit or attack
            if (d_lastDeployedCountry != null) {
                attack();
            } else {
                // Already deployed and attacked
                getPlayer().setCommitForThisTurn(true);
            }
        }
    }

    /**
     * Identifies the country to attack
     *
     * @return country to which attack should take place
     */
    @Override
    public Country toAttack() {
        Country[] enemyCountries = d_lastDeployedCountry.getEnemyNeighbours();
        // Find first enemy with chance to win
        Country attackFrom = toAttackFrom();
        if (enemyCountries.length > 1) {
            Random random = new Random(0);
            int randomIndex = random.nextInt(enemyCountries.length - 1);
            return enemyCountries[randomIndex];
        } else if (enemyCountries.length == 1) {
            return enemyCountries[0];
        } else {
            return null;
        }
    }

    /**
     * Identifies the country to attack from
     *
     * @return country from where attack should take place
     */
    @Override
    public Country toAttackFrom() {
        return d_lastDeployedCountry;
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
        Country countryWithHighestDeployment = getCountryWithHighestDeployment();
        if (countryWithHighestDeployment == null) {
            // This is the first deployment
            return getCountryWithMaxNeighbours();
        } else {
            // We have deployment, add forces to this country
            return countryWithHighestDeployment;
        }
    }

    /**
     * Attack with all the might into an enemy neighbouring country
     */
    private void attack() {
        // attack now!
        Country attackFrom = toAttackFrom();
        int availableArmyUnits = d_lastDeploymentUnits + ((attackFrom == null) ? 0 : attackFrom.getD_noOfArmies());
        int tenPercent = (int) Math.floor((0.1 * availableArmyUnits));
        int ninetyPercent = availableArmyUnits - tenPercent;
        AdvanceOrder advanceOrder = new AdvanceOrder(getPlayer(), attackFrom, toAttack(), ninetyPercent);
        String l_message = "[AdvanceCommand]: " + getPlayer().getD_name() + " requested Country " + attackFrom.getD_countryName() + " to attack " + toAttack().getD_countryName() + " with " + ninetyPercent + " army units.";
        LogEntryBuffer.getInstance().log(l_message);

        getPlayer().appendOrderToList(advanceOrder);
        d_lastDeployedCountry = null;
        d_lastDeploymentUnits = 0;
    }

    public Country getCountryWithHighestDeployment() {
        int currentMaxDeploymentCount = 0;
        Country countryWithMaxDeployment = null;
        Country[] ownedCountry = getPlayer().getD_ownedCountries().toArray(new Country[0]);
        for (Country country: ownedCountry) {
            int deploymentCount = country.getD_noOfArmies();
            if (deploymentCount > currentMaxDeploymentCount) {
                currentMaxDeploymentCount = deploymentCount;
                countryWithMaxDeployment = country;
            }
        }

        return countryWithMaxDeployment;
    }
    /**
     * Finds a country with max neighbours
     * @return country with max neighbours
     */
    public Country getCountryWithMaxNeighbours() {
        int currentNeighbourCount = 0;
        Country countryWithMaxNeighbours = null;
        Country[] ownedCountry = getPlayer().getD_ownedCountries().toArray(new Country[0]);
        for (Country country: ownedCountry) {
            int neighbourCount = country.getD_neighbors().size();
            if (neighbourCount > currentNeighbourCount) {
                currentNeighbourCount = neighbourCount;
                countryWithMaxNeighbours = country;
            }
        }

        return countryWithMaxNeighbours;
    }
}
