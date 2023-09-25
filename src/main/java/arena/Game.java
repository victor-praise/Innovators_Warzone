package main.java.arena;

import main.java.models.Continent;
import main.java.utils.Command;
import main.java.utils.CommandParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A singleton class describing all the high level game functionalities and resources
 * @author kevin on 2023-09-25
 * @version 1.0
 */
public class Game {
    private static Game d_sharedInstance = null;
    private List<Continent> d_continents;
    //List<Country> d_countries;
    private Game() {
        d_continents = new ArrayList<>();
    }

    public static synchronized Game sharedInstance() {
        if (d_sharedInstance == null) {
            d_sharedInstance = new Game();
        }

        return d_sharedInstance;
    }

    public void insertContinent(Continent p_continent) {
        if (p_continent == null) {
            System.out.println("[Undefined] can not insert a null value to list of continents");
        }
        d_continents.add(p_continent);
    }

    public boolean removeContinent(Continent p_continent) {
        if (p_continent == null) {
            System.out.println("[Undefined] can not remove a null value to list of continents");
            return false;
        }
        return d_continents.remove(p_continent);
    }

    public boolean removeContinentWithId(int p_continentId) {
        Continent l_continentToRemove = null;
        for (Continent l_continent:d_continents) {
            if (l_continentToRemove.getD_continentID() == p_continentId) {
                l_continentToRemove = l_continent;
                break;
            }
        }
        return removeContinent(l_continentToRemove);
    }

    public void setup() {
        System.out.println("--- Game setup ---");
        System.out.println("--- Enter commands to set up the map followed by 'commit' to begin game ---");
        System.out.println("--- At any point, you can give a command  'quit' to exit the game ---");
        String l_nextCommand = "";
        CommandParser l_parser = new CommandParser();

        while (!l_nextCommand.equals("quit")) {
            Scanner commandReader = new Scanner(System.in);
            l_nextCommand = commandReader.nextLine();
            Command l_command = l_parser.parseCommandStatement(l_nextCommand);
            if (l_command != null) {
                l_command.execute();
            }
        }
    }
}
