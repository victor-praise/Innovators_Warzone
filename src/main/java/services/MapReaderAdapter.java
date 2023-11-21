package main.java.services;

import main.java.models.Map;

import java.util.List;

/**
 * Adapter class for reading conquest map file.
 * @author Victor
 */
public class MapReaderAdapter extends MapReader{
    private ConquestMapReader l_conquestMapReader;

    /**
     * constructor that sets the conquest map file reader.
     *
     * @param p_conquestMapFileReader conquest map file reader
     */
    public MapReaderAdapter(ConquestMapReader p_conquestMapFileReader) {
        this.l_conquestMapReader = p_conquestMapFileReader;
    }

    /**
     * Adapter for reading different type of map file through adaptee.
     * @param p_map map to be set
     * @param p_file lines of loaded file
     */
    public void parseMapFile(Map p_map, List<String> p_file) {
        l_conquestMapReader.readConquestMap(p_map, p_file);
    }
}
