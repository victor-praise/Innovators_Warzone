package main.java.services;

import main.java.arena.Game;
import main.java.models.Continent;

import java.io.FileWriter;
import java.io.IOException;

/**
 *  This class is responsible for saving the map to a text file
 * @author Victor
 *
 *
 */
public class MapSaver {


    /**
     * ensures the current map has continents and countries before saving
     * @param p_fileWriter
     */
    public void saveMapToFile(FileWriter p_fileWriter) throws IOException {

        if(Game.sharedInstance().getD_map().getD_continents() != null && !Game.sharedInstance().getD_map().getD_continents().isEmpty()){
            saveContinentData(p_fileWriter);
        }
        if(Game.sharedInstance().getD_map().getD_countries() != null && !Game.sharedInstance().getD_map().getD_countries().isEmpty() ){
            saveCountryData(p_fileWriter);
        }

    }

    /**
     * Saves continent data from map
     * @param p_fileWriter
     */
    public void saveContinentData(FileWriter p_fileWriter) throws IOException {
        p_fileWriter.write(System.lineSeparator() + "[continents]" + System.lineSeparator());
        for (Continent l_continent : Game.sharedInstance().getD_map().getD_continents()) {
            String formattedLine = String.format("%s %d%s",
                    l_continent.getD_continentName(),
                    l_continent.getD_continentValue(),
                    System.lineSeparator());

            p_fileWriter.write(formattedLine);
        }
    }

    /**
     * saves Country data along with neighbours from map
     * @param p_fileWriter
     */
    public void saveCountryData(FileWriter p_fileWriter){}

}
