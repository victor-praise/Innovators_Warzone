package main.java.strategy;

import main.java.models.Country;
import main.java.orders.Order;

/**
 * @author kevin on 2023-11-24
 */
public class HumanPlayerStrategy extends PlayerStrategy {
    /**
     * Create a new order based on Strategy
     *
     * @return new order according to the strategy
     */
    @Override
    public Order createOrder() {
        return null;
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
}