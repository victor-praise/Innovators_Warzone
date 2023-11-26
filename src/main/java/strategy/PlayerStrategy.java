package main.java.strategy;

import main.java.models.Country;
import main.java.models.Player;

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
     * Create a new order based on Strategy and adds it to Player's order list.
     */
    public abstract void createOrder();

    /**
     * Returns the player associated with this strategy object
     * @return player associated with this
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player to which this strategy applies
     * @param player player to which this strategy applies
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

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
     * Identifies the country to deploy to
     * @return country to which deployment should take place
     */
    public abstract Country toDeploy();

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
