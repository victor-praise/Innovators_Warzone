package main.java.utils;

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
     * @see main.java.utils.Command
     */
    public Command parseCommandStatement(String p_commandString) {
        int l_indexOfFirstSpace = p_commandString.indexOf(" ");
        if (l_indexOfFirstSpace == -1) {
            BaseCommand l_baseCommand = inferBaseCommand(p_commandString);
            if (l_baseCommand == BaseCommand.None) {
                return null;
            } else {
                return new Command(l_baseCommand, null, null);
            }
        } else {
            String l_baseCommandString = p_commandString.substring(0, l_indexOfFirstSpace);
            String l_remainingToParse = p_commandString.substring(l_indexOfFirstSpace+1);
            BaseCommand l_baseCommand = inferBaseCommand(l_baseCommandString);

            if (l_remainingToParse.startsWith("-")) {
                Functionality l_function = parseFunctionalities(l_remainingToParse.substring(1));
                Functionality[] l_functions = new Functionality[1];
                l_functions[0] = l_function;

                if (l_baseCommand == BaseCommand.None) {
                    return null;
                } else {
                    return new Command(l_baseCommand, null, l_functions);
                }
            } else {
                String[] l_params = l_remainingToParse.split(" ");
                return new Command(l_baseCommand, l_params, null);
            }
        }
    }

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

    private BaseCommand inferBaseCommand(String commandString) {
        return BaseCommand.from(commandString);
    }
}