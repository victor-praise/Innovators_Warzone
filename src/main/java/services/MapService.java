package main.java.services;

import main.java.models.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
     * The loadmap method process map file.
     *

     * @param p_fileName map file name.
     * @return Map object after processing map file.

     */
    public Map loadMap(String p_fileName)  {
        Map l_map = new Map();
        List<String> l_fileLines = loadFile(p_fileName);

        if (null != l_fileLines && !l_fileLines.isEmpty()) {

           // new MapReader().parseMapFile( l_map, l_fileLines);

        }
        return l_map;
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
     * Generates absolute file path from the given map file.
     *
     * @param p_fileName amp filename
     * @return file path
     */
    public String getFilePath(String p_fileName){
        String l_filePath = new File("").getAbsolutePath();
        return l_filePath + File.separator + "src/main/resources" + File.separator + p_fileName;
    }
}
