package main.java.orders;

import main.java.models.Country;
import main.java.utils.logger.LogEntryBuffer;

/**
 * destroy half of the armies located on an opponent’s territory that is adjacent to one of the current player’s territories.
 * @author victor
 */
public class BombOrder implements Order{
    Country d_targetCountry;
    public BombOrder(Country l_targetCountry){
        this.d_targetCountry = l_targetCountry;
    }

    /**
     * Each order will have an execute method where the execution logic will be inserted
     */
    @Override
    public void execute() {
        int l_newNoArmies = d_targetCountry.getD_noOfArmies()/2;
        d_targetCountry.reduceArmyUnits(l_newNoArmies);
        String l_message = "[ BombOrder ] successfully bombed " + d_targetCountry.getD_countryName();
        LogEntryBuffer.getInstance().log(l_message);
    }
}
