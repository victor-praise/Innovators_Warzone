package main.java.commands;
import main.java.arena.Game;
import main.java.services.MapService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Command to load a map
 * @author Victor
 */
public class LoadMapCommand extends Command {

    /**
     * Creates a new Load Map command with given baseCommand and/or any params and/or any functionalities
     *
     * @param baseCommand     represents actual command input by the user, e.g. showmap
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public LoadMapCommand(BaseCommand baseCommand, String[] baseParams, Functionality[] functionalities) {
        super(baseCommand.LoadMap, baseParams, functionalities);
    }
    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute(){
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

            String l_filePath = new File("").getAbsolutePath();
            String l_file = l_filePath + File.separator + "res" + File.separator + l_filename;
            File l_fileToBeLoaded = new File(l_file);
            if(l_fileToBeLoaded.exists()){
                if (!l_filename.contains(".map")) {
                    l_filename = l_filename.concat(".map");
                }
                mapService.loadMap(l_filename);
            }
            else{
                System.out.println("[LoadMapCommand]: file does not exist, please provide the name of a file that exists ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
