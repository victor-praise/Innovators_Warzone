package main.java.strategy;

import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.Order;

/**
 * @author kevin on 2023-11-24
 * @version 1.3
 */
public abstract class PlayerStrategy {

    /**
     * Player to whom the strategy belongs
     */
    private Player player;

    /**
     * Create a new order based on Strategy
     * @return new order according to the strategy
     */
    public abstract Order createOrder();

    /**
     * Identifies the country to attack
     * @return country to which attack should take place
     */
    public abstract Country toAttack();

    /**
     * Identifies the country to attack from
     * @return country from where attack should take place
     */
    public abstract Country toAttackFrom();

    /**
     * Identifies country to move to
     * @return country to move to
     */
    public abstract Country toMoveFrom();

    /**
     * Identifies the country to defend
     * @return country to defend
     */
    public abstract Country toDefend();
}
