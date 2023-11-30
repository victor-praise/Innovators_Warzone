package main.java.services;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for saving the map to a text file
 * @author Victor
 */
public class MapSaver {


    /**
     * ensures the current map has continents and countries before saving
     * @param p_fileWriter instance of file writer
     * @throws IOException when writing data to files has exception
     */
    public void saveMapToFile(FileWriter p_fileWriter) throws IOException {
        Map l_currentMap = Game.sharedInstance().getD_map();
        if(l_currentMap.hasContinents()){
            saveContinentData(p_fileWriter);
        }
        if(l_currentMap.hasCountries()){
            saveCountryData(p_fileWriter);
        }
    }

    /**
     * Saves continent data from map
     * @param p_fileWriter instance of file writer
     * @throws IOException when writing data to files has exception
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
     * @param p_fileWriter instance of file writer
     * @throws IOException when writing data to files has exception
     */
    public void saveCountryData(FileWriter p_fileWriter) throws IOException {
        String l_countryData;
        String l_neighbours;
        List<String> l_neighboursList = new ArrayList<>();
        // Writes Countries along with neighbours to file
        p_fileWriter.write(System.lineSeparator() + "[countries]" + System.lineSeparator());
        for (Country l_country : Game.sharedInstance().getD_map().getD_countries()) {
             l_countryData = String.format("%d %s %d",
                    l_country.getD_countryID(),
                    l_country.getD_countryName(),
                    l_country.getD_continentID());

            p_fileWriter.write(l_countryData + System.lineSeparator());

            if (l_country.getD_neighbors() != null && !l_country.getD_neighbors().isEmpty()) {
                l_neighbours = String.valueOf(l_country.getD_countryID());
                l_neighbours += " " + l_country.getD_neighbors().stream().map(Object::toString).collect(Collectors.joining(" "));
                l_neighboursList.add(l_neighbours);
            }
        }
        // Writes Border data(neighbours) to the File
        if (!l_neighboursList.isEmpty()) {
            p_fileWriter.write(System.lineSeparator() + "[borders]" + System.lineSeparator());
            for (String l_borderStr : l_neighboursList) {
                p_fileWriter.write(l_borderStr + System.lineSeparator());
            }
        }
    }
}
