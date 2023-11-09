package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * destroy half of the armies located on an opponent’s territory that is adjacent to one of the current player’s territories.
 * @author victor
 */
public class BombOrder implements Order{
    Player d_issuingPlayer;
    Country d_targetCountry;

    /**
     * Destroy half of the armies located on an opponent’s territory that is adjacent to one of the current player’s territories
     *
     * @param d_issuingPlayer Player issuing the command
     * @param l_targetCountry Country to be bombed
     */
    public BombOrder(Player d_issuingPlayer, Country l_targetCountry) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.d_targetCountry = l_targetCountry;
    }

    /**
     * Each order will have an execute method where the execution logic will be inserted
     */
    @Override
    public void execute() {
        Player l_destinationOwner = d_targetCountry.getD_ownedBy();

        // If there are negotiations between the two players, ignore the command
        if (d_issuingPlayer.hasNegotiatedWith(l_destinationOwner)) {
            String l_message = "'" + d_issuingPlayer.getD_name() + "' and " + "'" + l_destinationOwner.getD_name() + "'" + "  have an existing negotiation pact. Will Ignore advance order";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        // If the target country is a NEUTRAL territory, bomb order would be ignored
        if (d_targetCountry.isD_isNeutralTerritory()) {
            String l_message = "'" + d_targetCountry.getD_countryName() + "' is a NEUTRAL territory. Bomb order has no effect";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        int l_newNoArmies = d_targetCountry.getD_noOfArmies()/2;
        d_targetCountry.reduceArmyUnits(l_newNoArmies);
        String l_message = "[ BombOrder ] successfully bombed " + d_targetCountry.getD_countryName();
        LogEntryBuffer.getInstance().log(l_message);
    }
}
