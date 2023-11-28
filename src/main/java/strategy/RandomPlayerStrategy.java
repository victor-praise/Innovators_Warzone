package main.java.strategy;

import main.java.arena.Game;
import main.java.models.Country;
import main.java.orders.AdvanceOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin on 2023-11-24
 */
public class RandomPlayerStrategy extends PlayerStrategy {

    public RandomPlayerStrategy(Strategy p_strategy) {
        super(p_strategy);
    }

    /**
     * Determines if we have ordered random tropp movement or not
     */
    private boolean hasMovedRandomly = false;

    /**
     * Determines if we have ordered random tropp movement or not
     */
    private boolean hasAttackedRandomly = false;

    /**
     * Keeping reference to attack from
     */
    private Country d_toAttackFrom = null;

    /**
     * Create a new order based on a Random Strategy
     * First deploy available units to a random country
     * Second, attack from a random country
     * Third, move army units of a random territory to another random territory
     */
    @Override
    public void createOrder() {
        int availableToDeploy = getPlayer().getD_assignedArmyUnits();
        if (availableToDeploy > 0) {
            // DeploymentStage
            int deploymentUnits = getPlayer().getD_assignedArmyUnits();
            deployArmyUnits(deploymentUnits);
        } else {
            // Can commit or attack
            if (!hasAttackedRandomly) {
                attack();
                hasAttackedRandomly = true;
            } else {
                if (hasMovedRandomly) {
                    // Already deployed, attacked, and moved
                    hasMovedRandomly = false;
                    hasAttackedRandomly = false;
                    getPlayer().setCommitForThisTurn(true);
                } else {
                    move();
                    hasMovedRandomly = true;
                }
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
        Country[] enemyCountries = d_toAttackFrom.getEnemyNeighbours();
        // Random Country to attack
        Country attackFrom = toAttackFrom();
        if (enemyCountries.length > 1) {
            int randomIndex = getRandomNumberBetween(0, enemyCountries.length - 1);
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
        List<Country> moreThanOneUnit = getPlayer().getD_ownedCountries().stream().filter(Country::hasMoreThanOneArmyUnit).toList();
        if (moreThanOneUnit.isEmpty()) {
            return d_lastDeployedCountry;
        } else {
            int size = moreThanOneUnit.size();
            if (size == 1) {
                return moreThanOneUnit.get(0);
            } else {
                int random = getRandomNumberBetween(5, 50);
                if (random % 2 == 0) {
                    return d_lastDeployedCountry;
                } else {
                    int index = getRandomNumberBetween(0, size - 1);
                    return moreThanOneUnit.get(index);
                }
            }
        }
    }

    /**
     * Identifies country to move to
     *
     * @return country to move to
     */
    @Override
    public Country toMoveFrom() {
        ArrayList<Country> moreThanOneUnit = new ArrayList<>(getPlayer().getD_ownedCountries().stream().filter(Country::hasMoreThanOneArmyUnit).toList());
        moreThanOneUnit.remove(d_lastDeployedCountry);
        int size = moreThanOneUnit.size();
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return moreThanOneUnit.get(0);
        } else {
            int random = getRandomNumberBetween(0, size - 1);
            return moreThanOneUnit.get(random);
        }
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
        List<Country> ownedCountries = getPlayer().getD_ownedCountries();
        if (ownedCountries.isEmpty()) {
            return null;
        } else if (ownedCountries.size() == 1) {
            return ownedCountries.get(0);
        } else {
            int randomCountryIndex = getRandomNumberBetween(0, ownedCountries.size() - 1);
            return ownedCountries.get(randomCountryIndex);
        }
    }

    /**
     * Attack with all the might into an enemy neighbouring country
     */
    private void attack() {
        // attack now!
        d_toAttackFrom = toAttackFrom();
        Country attackTo = toAttack();
        if (d_toAttackFrom == null) {
            // We don't have any country with more even one unit of army
            return;
        }
        int availableArmyUnits =  d_toAttackFrom.getD_noOfArmies() + ((d_lastDeployedCountry.getD_countryID() == d_toAttackFrom.getD_countryID()) ? d_lastDeploymentUnits : 0);
        int tenPercent = (int) Math.floor((0.1 * availableArmyUnits));
        int ninetyPercent = availableArmyUnits - tenPercent;
        AdvanceOrder advanceOrder = new AdvanceOrder(getPlayer(), d_toAttackFrom, attackTo, ninetyPercent);
        String l_message = "[RandomStrategy]: " + getPlayer().getD_name() + " requested Country " + d_toAttackFrom.getD_countryName() + " to attack " + attackTo.getD_countryName() + " with " + ninetyPercent + " army units.";
        LogEntryBuffer.getInstance().log(l_message);

        getPlayer().appendOrderToList(advanceOrder);
        d_toAttackFrom = null;
    }

    /**
     * Performs movement of troops from 'toMoveFrom()' to a random country
     */
    private void move() {
        Country toMoveFrom = toMoveFrom();
        if (toMoveFrom != null) {
            List<Integer> neighbours = toMoveFrom.getD_neighbors();
            int size = neighbours.size();
            int random = (size > 1) ? getRandomNumberBetween(0, size - 1) : 0;
            Country moveTo = Game.sharedInstance().getD_map().getCountry(neighbours.get(random));
            if (moveTo != null) {
                AdvanceOrder advanceOrder = new AdvanceOrder(getPlayer(), toMoveFrom, moveTo, toMoveFrom.getD_noOfArmies());
                getPlayer().appendOrderToList(advanceOrder);

                String l_message = "[RandomStrategy]: " + getPlayer().getD_name() + " requested Country " + toMoveFrom.getD_countryName() + " to move " + toMoveFrom.getD_noOfArmies() + " army units to " + moveTo.getD_countryName();
                LogEntryBuffer.getInstance().log(l_message);
            } else {
                System.out.println("MoveTo is null. Can not perform order to move");
            }
        }
        d_lastDeployedCountry = null;
        d_lastDeploymentUnits = 0;
    }
}
