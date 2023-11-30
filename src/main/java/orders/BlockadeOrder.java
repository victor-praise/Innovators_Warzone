package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * Triple the number of armies on one of the current player’s territories and make it a neutral territory
 *
 * @author kevin on 2023-11-04
 */
public class BlockadeOrder implements Order {

    Player d_issuingPlayer;
    Country targetCountry;

    /**
     * Triple the number of armies on one of the current player’s territories and make it a neutral territory
     *
     * @param d_issuingPlayer Player issuing the command
     * @param p_country
     */
    public BlockadeOrder(Player d_issuingPlayer, Country p_country) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.targetCountry = p_country;
    }

    /**
     * Each order will have an execute method where the execution logic will be inserted
     */
    @Override
    public void execute() {
        this.targetCountry.setD_noOfArmies(this.targetCountry.getD_noOfArmies() * 3);
        this.targetCountry.setD_isNeutralTerritory(true);

        String l_message = this.targetCountry.getD_countryName() + " is now a NEUTRAL territory";
        LogEntryBuffer.getInstance().log(l_message);
    }
}
