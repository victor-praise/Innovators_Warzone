package main.java.utils;

import main.java.commands.*;

import java.util.ArrayList;

import static java.lang.Math.max;

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
                String[] allSubstrings = parseRemainingString(l_remainingToParse);
                l_functionalities = new Functionality[allSubstrings.length];
                for (int index = 0; index < allSubstrings.length; index++) {
                    l_functionalities[index] = parseFunctionalities(allSubstrings[index]);
                }
            } else {
                l_baseParams = l_remainingToParse.split(" ");
            }
        }

        return new Command(l_baseCommand, l_baseParams, l_functionalities);
    }

    private String[] parseRemainingString(String p_remainingString) {
        ArrayList<String> functionalityStrings = new ArrayList<>();
        int splitStartIndex = 0;
        String l_remaining = p_remainingString;
        while (!l_remaining.isEmpty()) {
            int lastIndex = l_remaining.lastIndexOf("-");
            String lastFunctionalityString = l_remaining.substring(lastIndex + 1);
            functionalityStrings.add(lastFunctionalityString);
            l_remaining = l_remaining.substring(0, max(0, lastIndex - 1));
        }
        return functionalityStrings.toArray(new String[0]);
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