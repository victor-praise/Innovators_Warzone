package main.java.utils;

import main.java.commands.*;

/**
 * A parser for the user provided command string.
 * @author kevin wadera
 * @version 1.0
 */
public class CommandParser {

    /**
     * Given a user provided command, this will generate a Command object for a valid command, null otherwise
     * @param p_commandString the string representation of command input by user
     * @return Command object for a valid String, null for an invalid input.
     * @see Command
     */
    public Command parseCommandStatement(String p_commandString) {
        int l_indexOfFirstSpace = p_commandString.indexOf(" ");
        BaseCommand l_baseCommand = BaseCommand.None;
        String[] l_baseParams = null;
        Functionality[] l_functionalities = null;

        if (l_indexOfFirstSpace == -1) {
            l_baseCommand = inferBaseCommand(p_commandString);
        } else {
            String l_baseCommandString = p_commandString.substring(0, l_indexOfFirstSpace);
            String l_remainingToParse = p_commandString.substring(l_indexOfFirstSpace+1);
            l_baseCommand = inferBaseCommand(l_baseCommandString);

            if (l_remainingToParse.startsWith("-")) {
                Functionality l_function = parseFunctionalities(l_remainingToParse.substring(1));
                l_functionalities = new Functionality[1];
                l_functionalities[0] = l_function;
            } else {
                l_baseParams = l_remainingToParse.split(" ");
            }
        }

        Command l_command = null;
        if (l_baseCommand == BaseCommand.None) {
            return null;
        } else {
            switch (l_baseCommand) {
                case EditContinent:
                    l_command = new EditContinentCommand(l_baseParams, l_functionalities);
                    break;

                case EditCountry:
                    l_command = new EditCountryCommand(l_baseParams, l_functionalities);
                    break;

                case EditNeighbour:
                    l_command = new EditNeighbourCommand(l_baseParams, l_functionalities);
                    break;

                case EditMap:
                    l_command = new EditMapCommand(l_baseParams, l_functionalities);
                    break;

                default:
                    break;
            }
        }

        return l_command;
    }

    /**
     * Given a functionality sub-string in user input, generate a Functionality object
     * @param functionalityString substring of user command
     * @return Constructed functionality object
     */
    private Functionality parseFunctionalities(String functionalityString) {
        int indexOfFirstSpace = functionalityString.indexOf(" ");
        if (indexOfFirstSpace == -1) {
            BaseFunctionality functionality = BaseFunctionality.from(functionalityString);
            return  new Functionality(functionality, null);
        } else {
            String baseFunctionalityString = functionalityString.substring(0, indexOfFirstSpace);
            BaseFunctionality functionality = BaseFunctionality.from(baseFunctionalityString);
            String[] params = functionalityString.substring(indexOfFirstSpace+1).split(" ");
            return new Functionality(functionality, params);
        }
    }

    /**
     * Given a command string, generate a command object
     * @param commandString user input
     * @return Constructed command object
     */
    private BaseCommand inferBaseCommand(String commandString) {
        return BaseCommand.from(commandString);
    }
}