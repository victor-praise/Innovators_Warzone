package main.java.strategy;

import main.java.models.Country;

/**
 * @author kevin on 2023-11-24
 */
public class CheaterPlayerStrategy extends PlayerStrategy {
    /**
     * Create a new order based on Strategy and adds it to Player's order list
     *
     */
    @Override
    public void createOrder() {
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
        return null;
    }
}
