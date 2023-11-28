package main.java.strategy;

import main.java.arena.Game;
import main.java.models.Country;
import main.java.orders.AdvanceOrder;
import main.java.orders.DeployOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author kevin on 2023-11-24
 */
public class CheaterPlayerStrategy extends PlayerStrategy {

    public CheaterPlayerStrategy(Strategy p_strategy) {
        super(p_strategy);
    }

    /**
     * Create a new order based on Strategy and adds it to Player's order list
     *
     */
    @Override
    public void createOrder() {
        Country[] standingArmies = allTerritoriesWithArmies();

        // Follow up with attacks on enemy territories
        Country[] countryWithCapabilityToStrike = Arrays.stream(allTerritoriesWithArmies())
                .filter(Country::hasAdjacentEnemyTerritory)
                .toList()
                .toArray(new Country[0]);

        strikeWithMight(countryWithCapabilityToStrike);

        // We first deploy available units
        int availableUnitsToDeploy = getPlayer().getD_assignedArmyUnits();
        Country[] vulnerableTerritories = territoriesWithEnemyBorders();
        if (vulnerableTerritories.length != 0) {
            deployArmyUnitsEvenly(availableUnitsToDeploy, vulnerableTerritories);

            // Reduce army units for this player
            getPlayer().reduceArmyUnits(availableUnitsToDeploy);
        }

        // Strengthen borders, move armies from safe countries to border
        for (Country borderCountry : vulnerableTerritories) {
            Country[] safeNeighbours = borderCountry.safeNeighbours();
            for (Country neighbour : safeNeighbours) {
                if (neighbour.getD_noOfArmies() > 0) {
                    AdvanceOrder advanceOrder = new AdvanceOrder(getPlayer(), neighbour, borderCountry, neighbour.getD_noOfArmies());
                    String l_message = "[Cheater]: Advancing " + neighbour.getD_noOfArmies() + " troops from inner country: " + neighbour.getD_countryName() + " to border country: " + borderCountry.getD_countryName();
                    LogEntryBuffer.getInstance().log(l_message);
                    advanceOrder.execute();
                }
            }
        }

        // Give Commit order
        getPlayer().setCommitForThisTurn(true);
    }

    /**
     * Identifies the country to attack
     *
     * @return country to which attack should take place
     */
    @Override
    public Country toAttack() {
        return null;
    }

    /**
     * Identifies the country to attack from
     *
     * @return country from where attack should take place
     */
    @Override
    public Country toAttackFrom() {
        return null;
    }

    /**
     * Identifies country to move to
     *
     * @return country to move to
     */
    @Override
    public Country toMoveFrom() {
        return null;
    }

    /**
     * Identifies the country to defend
     *
     * @return country to defend
     */
    @Override
    public Country toDefend() {
        return null;
    }

    /**
     * Identifies the country to deploy to
     * @return country to which deployment should take place
     */
    @Override
    public Country toDeploy() {
        return null;
    }

    /**
     * Returns all territories owned by this player having more than one troop
     * @return all territories with more than one troop
     */
    private Country[] allTerritoriesWithArmies() {
        List<Country> standingArmies = getPlayer().getD_ownedCountries().stream().filter(Country::hasMoreThanOneArmyUnit).toList();
        return standingArmies.toArray(new Country[0]);
    }

    /**
     * Get all territories owned by this player having border with enemy territory
     * @return territories having borders with enemies
     */
    private Country[] territoriesWithEnemyBorders() {
        List<Country> borders = getPlayer().getD_ownedCountries().stream().filter(Country::hasAdjacentEnemyTerritory).toList();
        return borders.toArray(new Country[0]);

    }

    /**
     * Evenly deploys a given amount of troops to the provided list of countries
     * @param armyUnitsToDeploy number of troops to distribute
     * @param territories array of countries where deployment is required
     */
    private void deployArmyUnitsEvenly(int armyUnitsToDeploy, Country[] territories) {
        int baseUnit = armyUnitsToDeploy / territories.length;
        int remainder = armyUnitsToDeploy % territories.length;
        int index = 0;
        for (Country territory: territories) {
            int units = baseUnit + ((index++ < remainder) ? 1 : 0);
            if (units == 0) {
                break;
            }
            DeployOrder deployOrder = new DeployOrder(getPlayer(), territory, units);
            deployOrder.execute();
            String l_message = "[Cheater]: Deploying  " + units + " troops to country: " + territory.getD_countryName() ;
            LogEntryBuffer.getInstance().log(l_message);
        }
    }

    /**
     * Executes Advance orders from a list of countries, advances to first enemy territory in neighbours list
     * @param countriesWithMight countries having capability to strike
     */
    private void strikeWithMight(Country[] countriesWithMight) {
        for (Country country: countriesWithMight) {
            Optional<Integer> firstEnemy = country.getD_neighbors().stream().filter(neighbourId -> {
                Country neighbour = Game.sharedInstance().getD_map().getCountry(neighbourId);
                return !neighbour.getD_ownedBy().getD_name().equals(country.getD_ownedBy().getD_name());
            }).findFirst();

            if (firstEnemy.isPresent()) {
                int enemyId = firstEnemy.get();
                Country enemyCountry = Game.sharedInstance().getD_map().getCountry(enemyId);
                AdvanceOrder advanceOrder = new AdvanceOrder(getPlayer(), country, enemyCountry, country.getD_noOfArmies());
                advanceOrder.execute();
            }
        }
    }
}
