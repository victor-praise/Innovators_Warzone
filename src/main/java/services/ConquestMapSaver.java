package main.java.services;

import main.java.arena.Game;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;

import java.io.FileWriter;
import java.io.IOException;


/**
 * Adapter class for saving conquest map file.
 * @author Victor
 */
public class ConquestMapSaver {

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
        p_fileWriter.write(System.lineSeparator() + "[Continents]" + System.lineSeparator());
        for (Continent l_continent : Game.sharedInstance().getD_map().getD_continents()) {
            p_fileWriter.write(
                    l_continent.getD_continentName().concat("=").concat(String.valueOf(l_continent.getD_continentValue()))
                            + System.lineSeparator());
        }
        p_fileWriter.write(System.lineSeparator());
    }

    /**
     * saves Country data along with neighbours from map
     * @param p_fileWriter instance of file writer
     * @throws IOException when writing data to files has exception
     */
    public void saveCountryData(FileWriter p_fileWriter) throws IOException {
        String l_countryData;
        // Writes Countries along with neighbours to file

        p_fileWriter.write(System.lineSeparator() + "[Territories]" + System.lineSeparator());
        for (Country l_country : Game.sharedInstance().getD_map().getD_countries()) {

            l_countryData = l_country.getD_countryName().concat(",null,null,").concat(Game.sharedInstance().getD_map().getContinent(l_country.getD_continentID()).getD_continentName());

            if (l_country.getD_neighbors() != null && !l_country.getD_neighbors().isEmpty()) {
                    for(Integer l_neighbour: l_country.getD_neighbors()){
                        l_countryData = l_countryData.concat(",").concat(Game.sharedInstance().getD_map().getCountry(l_neighbour).getD_countryName());
                    }
            }
            p_fileWriter.write(l_countryData + System.lineSeparator());
        }

    }
}
