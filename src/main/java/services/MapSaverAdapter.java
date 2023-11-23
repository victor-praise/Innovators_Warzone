package main.java.services;

import java.io.FileWriter;
import java.io.IOException;

public class MapSaverAdapter extends MapSaver{
    /**
     * Conquest map saver.
     */
    private ConquestMapSaver l_conquestMapSaver;

    /**
     * constructor for setting conquest map file Saver.
     *
     * @param p_conquestMapSaver conquest map file Writer
     */
    public MapSaverAdapter(ConquestMapSaver p_conquestMapSaver) {
        this.l_conquestMapSaver = p_conquestMapSaver;
    }


    /**
     * Adapter for writing to different type of map file through adaptee.
     *
     * @param l_writer file writer
     */
    public void saveMapToFile(FileWriter l_writer) throws IOException {
        l_conquestMapSaver.saveMapToFile(l_writer);
    }
}
