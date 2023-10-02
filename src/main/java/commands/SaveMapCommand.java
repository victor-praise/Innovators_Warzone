package main.java.commands;

import main.java.services.MapService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Command to save the map contents to a file
 * @author kevin on 2023-10-01
 */
public class SaveMapCommand extends Command{

    /**
     * Creates a new Command object with given baseCommand and/or any params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public SaveMapCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.SaveMap, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (functionalities != null && functionalities.length != 0) {
            System.out.println("[SaveMapCommand]: No functionality is supported for SaveMap command");
            return;
        }

        MapService mapService = new MapService();
        String l_filename;

        if (baseParams == null) {
            System.out.println("[SaveMapCommand]: file name is mandatory ");
            return;
        } else {
            Optional<String> optionalFileName = Arrays.stream(baseParams).findFirst();
            l_filename = optionalFileName.orElse(null);
        }

        try {
            if (l_filename == null || l_filename.isBlank()) {
                System.out.println("[SaveMapCommand]: file name is mandatory ");
                return;
            }

            if (!l_filename.endsWith(".map")) {
                l_filename = l_filename.concat(".map");
            }
            mapService.saveMap(l_filename);
            System.out.println("[SaveMap]: Map saved successfully. Any new operation will be for applied to a new map");
        } catch (IOException error) {
            System.out.println("[SaveMapCommand]: Error while saving map to: " + l_filename + " " + error.getLocalizedMessage());
            throw new RuntimeException(error);
        }
    }

}
