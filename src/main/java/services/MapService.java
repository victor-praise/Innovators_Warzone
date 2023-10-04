package main.java.services;

import main.java.arena.Game;
import main.java.models.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for  continents on the map
 *
 * @author Victor
 */
public class MapService {

    /**
     * The loadmap method process map file.
     *
     * @param p_fileName map file name.
     * @return Map object after processing map file.
     */
    public void loadMap(String p_fileName) {
        Map l_map = new Map();
        if (p_fileName != null) {
            List<String> l_file = loadFile(p_fileName);
            if (l_file != null && !l_file.isEmpty()) {
                new MapReader().readMapFile(l_map, l_file);
            }
        }
        Game.sharedInstance().setD_map(l_map);
    }

    /**
     * The loadFile method load and read map file.
     *
     * @param p_fileName map file name to load.
     * @return List of lines from map file.
     */
    public List<String> loadFile(String p_fileName) {

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
     *
     * @param p_filePath consists of base filepath
     * @throws IOException triggered in case the file does not exist or the file name is invalid
     */
    public void editMap(String p_filePath) throws IOException {
        if (!p_filePath.contains(".map")) {
            p_filePath = p_filePath.concat(".map");
        }
        String l_filePath = getFilePath(p_filePath);
        File l_fileToBeEdited = new File(l_filePath);
        loadMap(l_fileToBeEdited.exists() ? p_filePath : null);
    }

    /**
     * Generates absolute file path from the given map file.
     *
     * @param p_fileName amp filename
     * @return file path
     */
    public String getFilePath(String p_fileName) {
        String l_filePath = new File("").getAbsolutePath();
        return l_filePath + File.separator + "res" + File.separator + p_fileName;
    }
}
