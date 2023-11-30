package main.java.commands;

import main.java.models.Player;

/**
 * @author kevin on 2023-10-03
 */
public class PlayerOrderCommand extends Command {
    public Player d_issuingPlayer;

    /**
     * Creates a new Order command for a given player
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public PlayerOrderCommand(Player p_player, BaseCommand command, String[] baseParams, Functionality[] functionalities) {
        super(command, baseParams, functionalities);
        this.d_issuingPlayer = p_player;
    }

    /**
     * Checks if there are still some troops to be deployed
     * @return true if there are troop deployment pending, false otherwise
     */
    public boolean isDeploymentPending() {
        return (d_issuingPlayer.getD_assignedArmyUnits() > 0);
    }
}
