package main.java.utils;

public class CommandParser {

    public Command parseCommandStatement(String commandString) {
        int indexOfFirstSpace = commandString.indexOf(" ");
        if (indexOfFirstSpace == -1) {
            BaseCommand baseCommand = inferBaseCommand(commandString);
            if (baseCommand == BaseCommand.None) {
                return null;
            } else {
                return new Command(baseCommand, null, null);
            }
        } else {
            String baseCommandString = commandString.substring(0, indexOfFirstSpace);
            String remainingToParse = commandString.substring(indexOfFirstSpace+1);
            BaseCommand baseCommand = inferBaseCommand(baseCommandString);

            if (remainingToParse.startsWith("-")) {
                Functionality function = parseFunctionalities(remainingToParse.substring(1));
                Functionality[] functions = new Functionality[1];
                functions[0] = function;

                if (baseCommand == BaseCommand.None) {
                    return null;
                } else {
                    return new Command(baseCommand, null, functions);
                }
            } else {
                String[] params = remainingToParse.split(" ");
                return new Command(baseCommand, params, null);
            }
        }
    }

    private Functionality parseFunctionalities(String functionalityString) {
        return new Functionality(BaseFunctionality.Add, null);
    }
    private BaseCommand inferBaseCommand(String commandString) {
        return BaseCommand.from(commandString);
    }
}