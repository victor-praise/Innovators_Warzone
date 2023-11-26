package main.java.strategy;

import main.java.commands.Command;
import main.java.models.Country;
import main.java.utils.OrderParser;
import main.java.utils.logger.LogEntryBuffer;

import java.util.Scanner;

/**
 * @author kevin on 2023-11-24
 */
public class HumanPlayerStrategy extends PlayerStrategy {
    /**
     * Create a new order for human, i.e. ask user for a valid order
     *
     */
    @Override
    public void createOrder() {
        System.out.println("Player [" + getPlayer().getD_name() + "] needs to issue order: ");
        getPlayer().displayOwnedCountriesWithTroopCount();
        Scanner inputReader = new Scanner(System.in);
        String l_nextOrder = inputReader.nextLine();
        Command l_command = OrderParser.parseOrderStatement(getPlayer(), l_nextOrder);
        if (l_command != null) {
            l_command.execute();
        } else {
            LogEntryBuffer.getInstance().log("Valid commands in state [Attack] are: ");
            LogEntryBuffer.getInstance().log("1. deploy countryID numarmies");
            LogEntryBuffer.getInstance().log("2. advance countrynamefrom countynameto numarmies");
            LogEntryBuffer.getInstance().log("3. bomb countryID");
            LogEntryBuffer.getInstance().log("4. blockade countryID");
            LogEntryBuffer.getInstance().log("5. airlift sourcecountryID targetcountryID numarmies");
            LogEntryBuffer.getInstance().log("6. negotiate playerID");
            LogEntryBuffer.getInstance().log("commit/quit");
            LogEntryBuffer.getInstance().log(" --- ");
        }
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
}
