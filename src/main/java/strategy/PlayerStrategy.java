package main.java.strategy;

import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.DeployOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Random;

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
     * Country where last deployment order took place
     */
    protected Country d_lastDeployedCountry = null;

    /**
     * Army units used during last deployment order
     */
    protected int d_lastDeploymentUnits = 0;

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

    /**
     * deploy a given number of troops to country determied from 'toDeploy()'
     * @param l_unitsToDeploy
     */
    protected void deployArmyUnits(int l_unitsToDeploy) {
        // we need to deploy
        Country toDeploy = toDeploy();
        if (toDeploy != null) {
            DeployOrder deployOrder = new DeployOrder(getPlayer(), toDeploy, l_unitsToDeploy);
            getPlayer().appendOrderToList(deployOrder);

            // Reduce army units for this player
            getPlayer().reduceArmyUnits(l_unitsToDeploy);
            // Inform about remaining army units
            String l_message = "[PlayerStrategy]: Remaining Units of army to deploy: " + getPlayer().getD_assignedArmyUnits();
            LogEntryBuffer.getInstance().log(l_message);
        }

        // save values
        d_lastDeployedCountry = toDeploy;
        d_lastDeploymentUnits = l_unitsToDeploy;
    }

    /**
     * Generates a random number between low and high
     * @param low lowest value
     * @param high highest value
     * @return random number
     */
    protected int getRandomNumberBetween(int low, int high) {
        Random random = new Random(high - low);
        return random.nextInt(low, high);
    }
}
