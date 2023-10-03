package main.java.commands;

import main.java.services.MapService;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

/**
 * Command to load a map
 *
 * @author Victor
 */
public class LoadMapCommand extends Command {

    /**
     * Creates a new Load Map command with given baseCommand and/or any params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public LoadMapCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.LoadMap, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (functionalities != null && functionalities.length != 0) {
            System.out.println("[LoadMapCommand]: No functionality is supported for EditMap command");
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
                System.out.println("[LoadMapCommand]: file name is mandatory ");
                return;
            }

            if (!l_filename.endsWith(".map")) {
                l_filename = l_filename.concat(".map");
            }

            String l_filePath = new File("").getAbsolutePath();
            String l_file = l_filePath + File.separator + "res" + File.separator + l_filename;
            File l_fileToBeLoaded = new File(l_file);
            if (l_fileToBeLoaded.exists()) {
                mapService.loadMap(l_filename);
            } else {
                System.out.println("[LoadMapCommand]: file does not exist, please provide the name of a file that exists ");
            }
        } catch (Exception e) {
            System.out.println("[LoadMapCommand]: Error reading from file : " + e.getMessage());
        }
    }
}