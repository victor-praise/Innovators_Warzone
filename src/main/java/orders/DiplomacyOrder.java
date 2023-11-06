package main.java.orders;

import main.java.models.Player;

/**
 * A diplomacy object
 *
 * @author mohammadalshariar
 */
public class DiplomacyOrder implements Order {

    /**
     * Player issuing to negotiate order.
     */
    Player d_issuingPlayer;

    /**
     *  Player with whom you are negotiating
     */
    Player d_targetedPlayer;


    /**
     * initiate a diplomacy
     *
     * @param d_issuingPlayer player who initiated negotiation
     * @param d_targetedPlayer player with whom another player negotiate
     */
    public DiplomacyOrder(Player d_issuingPlayer, Player d_targetedPlayer) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.d_targetedPlayer = d_targetedPlayer;
    }

    @Override
    public void execute() {
    }
}
