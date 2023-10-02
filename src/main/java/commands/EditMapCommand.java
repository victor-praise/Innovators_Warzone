package main.java.commands;

import main.java.arena.Game;
import main.java.models.Map;
import main.java.services.MapService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Command to edit map contents of a given file
 * if the file doesn't exist, a new map is created from scratch
 * @author kevin on 2023-09-30
 */
public class EditMapCommand extends Command {

    /**
     * Creates a new Command object with given baseCommand and/or any params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public EditMapCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.EditMap, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (functionalities != null && functionalities.length != 0) {
            System.out.println("[EditMapCommand]: No functionality is supported for EditMap command");
            return;
        }

        MapService mapService = new MapService();
        String l_filename;

        if (baseParams == null) {
           l_filename = null;
        } else {
            Optional<String> optionalFileName = Arrays.stream(baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                System.out.println("[EditMapCommand]: file name is mandatory ");
                return;
            }
            mapService.editMap(l_filename);
        } catch (IOException e) {
            System.out.println("[EditMapCommand]: Error executing edit map command: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
