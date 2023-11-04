package main.java.utils;

import main.java.commands.*;
import main.java.models.Player;

/**
 * Understands a given command by the player
 *
 * @author kevin on 2023-10-03
 */
public class OrderParser {

    public static Command parseOrderStatement(Player p_player, String p_orderString) {
        int l_indexOfFirstSpace = p_orderString.indexOf(" ");
        BaseCommand l_orderCommand = BaseCommand.None;
        String[] l_orderParams = null;
        Functionality[] l_functionalities = null;
        Command l_command = null;

        if (l_indexOfFirstSpace == -1) {
            l_orderCommand = inferOrderCommand(p_orderString);
        } else {
            String l_OrderString = p_orderString.substring(0, l_indexOfFirstSpace);
            String l_remainingToParse = p_orderString.substring(l_indexOfFirstSpace+1);
            l_orderCommand = inferOrderCommand(l_OrderString);
            // For now assuming that orders will not have functionalities, only parameters
            l_orderParams = l_remainingToParse.split(" ");
        }

        if (l_orderCommand != BaseCommand.None) {
            switch (l_orderCommand) {
                case DeployOrder:
                    l_command = new DeployOrderCommand(p_player, l_orderParams);
                    break;

                case Quit:
                    l_command = new QuitCommand();
                    break;

                default:
                    break;
            }
        }

        return l_command;
    }


    /**
     * Given a command string, generate a command object
     * @param commandString user input
     * @return Constructed command object
     */
    private static BaseCommand inferOrderCommand(String commandString) {
        BaseCommand l_command = BaseCommand.from(commandString);
        return isOrderCommand(l_command) ? l_command : BaseCommand.None;
    }

    private static boolean isOrderCommand(BaseCommand p_command) {
        //TODO: Create an array of possible orders and verify that the command passed is in this array.
        BaseCommand[] possibleOrders = {BaseCommand.DeployOrder, BaseCommand.Quit};
        for (BaseCommand order: possibleOrders) {
            if (p_command == order) {
                return true;
            }
        }
        return false;
    }
}
