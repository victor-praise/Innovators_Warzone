package main.java.services;

import main.java.arena.Game;
import main.java.exceptions.MapInvalidException;
import main.java.models.Map;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  This class is responsible for  continents on the map
 * @author Victor
 *
 *
 */
public class MapService {

    /**
     * The loadMap method process map file.
     * @param p_fileName map file name.
     */
    public void loadMap(String p_fileName)  {
        Map l_map = new Map();
        if (p_fileName != null) {
            List<String> l_file = loadFile(p_fileName);
            if (l_file != null && !l_file.isEmpty()) {
                new MapReader().readMapFile( l_map, l_file);
            }
        } else {
            System.out.println("[Map Service]: Unknown file name, Creating new map from scratch");
        }
        Game.sharedInstance().setD_map(l_map);
    }

    /**
     * The loadFile method load and read map file.
     *
     * @param p_fileName map file name to load.
     * @return List of lines from map file.

     */
    public List<String> loadFile(String p_fileName){

        String l_filePath = getFilePath(p_fileName);
        List<String> l_lineList = new ArrayList<>();

        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(l_filePath));
            l_lineList = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (IOException ignored) {

        }
        return l_lineList;
    }

    /**
     * Method is responsible for creating a new map if map to be edited does not
     * exist, and if it exists it parses the map file to game state object.
     * @param p_filePath consists of base filepath
     * @throws IOException triggered in case the file does not exist or the file name is invalid
     */
    public void editMap(String p_filePath) throws IOException {

        if (!p_filePath.endsWith(".map")) {
            p_filePath = p_filePath.concat(".map");
        }
    
        String l_filePath = getFilePath(p_filePath);
        File l_fileToBeEdited = new File(l_filePath);
    
        if (l_fileToBeEdited.exists()) {
            loadMap(p_filePath);
        }
    }

    /**
     * Method is responsible for saving a map
     * @param p_fileName file name for map to be saved as
     * @throws IOException when file could not be written
     * @throws MapInvalidException If the map is invalid.
     */
    public void saveMap(String p_fileName) throws IOException, MapInvalidException {

        if(Game.sharedInstance().getD_map() != null ){
            Map l_currentMap = Game.sharedInstance().getD_map();
        
            if (l_currentMap != null && l_currentMap.validate()) {
                String filePath = getFilePath(p_fileName);
                Files.deleteIfExists(Paths.get(filePath));
                FileWriter l_fileWriter = new FileWriter(filePath);
                l_fileWriter.write("; map: " + p_fileName + System.lineSeparator());
                new MapSaver().saveMapToFile(l_fileWriter);
                l_fileWriter.close();
            } else {
                System.out.println("Map cannot be saved");
            }
        }
    }
    /**
     * Generates absolute file path from the given map file.
     *
     * @param p_fileName amp filename
     * @return file path
     */
    public String getFilePath(String p_fileName){
        String l_filePath = new File("").getAbsolutePath();
        return l_filePath + File.separator + "res" + File.separator + p_fileName;
    }
}
