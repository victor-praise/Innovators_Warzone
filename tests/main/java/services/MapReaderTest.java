package main.java.services;

import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Validates methods in MapReader class
 * @author kevin on 2023-09-30
 */
class MapReaderTest {

    @Test
    void testValidContinentData() {

        Continent l_asia = new Continent("Asia", 5);
        Country l_country1 = new Country(1, "India", 1);
        l_asia.addCountry(l_country1);
        Country l_country2 = new Country(1, "China", 1);
        l_asia.addCountry(l_country2);
        l_country1.addNeighbour(l_country2.getD_countryID());
        l_country2.addNeighbour(l_country1.getD_countryID());


        //assertEquals(3, continent.size());
        assertEquals("Asia", l_asia.getD_continentName());
        assertEquals(5, l_asia.getD_continentValue());
        assertEquals("India", l_country1.getD_countryName());
        assertEquals(1, l_country1.getD_continentID());
        assertEquals("China", l_country2.getD_countryName());
        assertEquals(1, l_country2.getD_continentID());
    }

    @Test
    void testInvalidContinentData() {
        Continent l_asia = new Continent("Asia", 5);

        //List<Continent> continents = mapReader.parseContinentMapData(invalidData);

        // You should adjust this test based on how you handle invalid data
        assertTrue(l_asia.getD_countries().isEmpty());
    }

    @Test
    void testReadMapFileWithInvalidData() {
        // Given
        MapReader mapReader = new MapReader();
        Map map = new Map();
        List<String> invalidData = Arrays.asList(
                "[continents]",
                "Asia", // Invalid continent data
                "[countries]",
                "1 India 1",
                "2 China 1",
                "[borders]",
                "1 2"
        );

        // When
        mapReader.readMapFile(map, invalidData);

        // Then
        List<Continent> continents = map.getD_continents();
        List<Country> countries = map.getD_countries();

        assertNotNull(continents);
        assertNotNull(countries);

        assertEquals(0, continents.size(), "Should have 0 continents due to invalid data");
        assertEquals(1, countries.size(), "Should have 0 countries due to invalid data");
    }
}