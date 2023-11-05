package main.java.orders;

import main.java.models.Country;

public class AdvanceOrder implements Order {
    Country d_sourceCountry;
    Country d_destinationCountry;
    int d_armyUnitsToAdvance;

    public AdvanceOrder(Country p_sourceCountry, Country p_destinationCountry, int p_armyUnits) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_destinationCountry = p_destinationCountry;
        this.d_armyUnitsToAdvance = p_armyUnits;
    }

    @Override
    public void execute() {
    
        // Advance army units based on ownership rules (60-70)
    }
}
