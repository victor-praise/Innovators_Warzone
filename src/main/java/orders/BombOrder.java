package main.java.orders;

import main.java.models.Country;
import main.java.models.Player;
import main.java.utils.logger.LogEntryBuffer;

/**
 * destroy half of the armies located on an opponent’s territory that is adjacent to one of the current player’s territories.
 * @author victor
 */
public class BombOrder implements Order {
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

//    /**
//     * Each order will have an execute method where the execution logic will be inserted
//     */
//    @Override
//    public void execute() {
//        Player l_destinationOwner = d_targetCountry.getD_ownedBy();
//
//        // If there are negotiations between the two players, ignore the command
//        if (d_issuingPlayer.hasNegotiatedWith(l_destinationOwner)) {
//            String l_message = "'" + d_issuingPlayer.getD_name() + "' and " + "'" + l_destinationOwner.getD_name() + "'" + "  have an existing negotiation pact. Will Ignore advance order";
//            LogEntryBuffer.getInstance().log(l_message);
//            return;
//        }
//
//        // If the target country is a NEUTRAL territory, bomb order would be ignored
//        if (d_targetCountry.isD_isNeutralTerritory()) {
//            String l_message = "'" + d_targetCountry.getD_countryName() + "' is a NEUTRAL territory. Bomb order has no effect";
//            LogEntryBuffer.getInstance().log(l_message);
//            return;
//        }
//
//        int l_newNoArmies = d_targetCountry.getD_noOfArmies()/2;
//        d_targetCountry.reduceArmyUnits(l_newNoArmies);
//        String l_message = "[ BombOrder ] successfully bombed " + d_targetCountry.getD_countryName();
//        LogEntryBuffer.getInstance().log(l_message);
//    }
//}

    /**
     * Executes a bomb order on the target country.
     * If there are negotiations between the issuing player and the target country's owner, the order is ignored.
     * If the target country is a neutral territory, the bomb order has no effect.
     * Otherwise, the target country's armies are reduced by half.
     */
    @Override
    public void execute() {
        Player l_destinationOwner = d_targetCountry.getD_ownedBy();

        if (hasNegotiationWithDestination(l_destinationOwner)) {
            logNegotiationIgnored(l_destinationOwner);
            return;
        }

        if (isNeutralTerritory()) {
            logNeutralTerritoryIgnored();
            return;
        }

        reduceArmiesByHalf();
    }

    /**
     * Checks if there is an existing negotiation pact between the issuing player and the destination owner.
     *
     * @param p_destinationOwner The owner of the target country.
     * @return {@code true} if there is a negotiation pact, {@code false} otherwise.
     */
    private boolean hasNegotiationWithDestination(Player p_destinationOwner) {
        return d_issuingPlayer.hasNegotiatedWith(p_destinationOwner);
    }

    /**
     * Logs a message indicating that the bomb order is ignored due to an existing negotiation pact.
     *
     * @param p_destinationOwner The owner of the target country.
     */
    private void logNegotiationIgnored(Player p_destinationOwner) {
        String message = "'" + d_issuingPlayer.getD_name() + "' and '" + p_destinationOwner.getD_name() +
                "' have an existing negotiation pact. Will ignore bomb order";
        LogEntryBuffer.getInstance().log(message);
    }

    /**
     * Checks if the target country is a neutral territory.
     *
     * @return {@code true} if the target country is neutral, {@code false} otherwise.
     */
    private boolean isNeutralTerritory() {
        return d_targetCountry.isD_isNeutralTerritory();
    }

    /**
     * Logs a message indicating that the bomb order is ignored as the target country is a neutral territory.
     */
    private void logNeutralTerritoryIgnored() {
        String l_message = "'" + d_targetCountry.getD_countryName() + "' is a NEUTRAL territory. Bomb order has no effect";
        LogEntryBuffer.getInstance().log(l_message);
    }

    /**
     * Reduces the target country's armies by half and logs a successful bombing message.
     */
    private void reduceArmiesByHalf() {
        int l_newNoArmies = d_targetCountry.getD_noOfArmies() / 2;
        d_targetCountry.reduceArmyUnits(l_newNoArmies);
        String message = "[ BombOrder ] successfully bombed " + d_targetCountry.getD_countryName();
        LogEntryBuffer.getInstance().log(message);
    }
}
