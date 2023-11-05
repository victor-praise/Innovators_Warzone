package main.java.orders;

import main.java.models.Country;

/**
 * @author sadiqali
 * Advances a certain number of army units from player-owned source country to destination. Destination country may or may not be owned by another player.
 */
public class AdvanceOrder implements Order {
    Country d_sourceCountry;
    Country d_destinationCountry;
    int d_armyUnitsToAdvance;

    public AdvanceOrder(Country p_sourceCountry, Country p_destinationCountry, int p_armyUnits) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_destinationCountry = p_destinationCountry;
        this.d_armyUnitsToAdvance = p_armyUnits;
    }

    /**
     * execute function
     */
    @Override
    public void execute() {
    
        // Advance army units based on ownership rules (60-70)
    }
}
