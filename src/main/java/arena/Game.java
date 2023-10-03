package main.java.arena;

import main.java.commands.BaseCommand;
import main.java.commands.Command;
import main.java.models.Continent;
import main.java.models.Country;
import main.java.models.Map;
import main.java.models.Player;
import main.java.utils.CommandParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A singleton class describing all the high level game functionalities and resources
 *
 * @author kevin on 2023-09-25
 * @version 1.0
 */
public class Game {
    /**
     * private instance of single Game class
     */
    private static Game d_sharedInstance = null;

    /**
     * private list of all the players
     */
    private List<Player> d_players; //need to know where will this list go

    /**
     * The current Map object in the game.
     */
    private Map d_map = null;

    /**
     * constructor being made private to prevent random instance creation, always use sharedInstance()
     */
    private Game() {
    }

    /**
     * We keep a singleton instance of Game which is accessible throughout the game engine
     *
     * @return an existing instance if it exists, otherwise returns a newly created instance
     */
    public static synchronized Game sharedInstance() {
        if (d_sharedInstance == null) {
            d_sharedInstance = new Game();
        }

        return d_sharedInstance;
    }

    /**
     * Returns current map being edited or a new map when there are no current map
     *
     * @return current map object
     */
    public Map getD_map() {
        if (d_map != null) {
            return d_map;
        }
        d_map = new Map();
        return d_map;
    }

    /**
     * Attempts to set a new map object. Fails if current map object is not null.
     * Can be used to reset the map object too by passing null.
     *
     * @param p_map new map object to set
     */
    public void setD_map(Map p_map) {
        if (d_map == null || p_map == null) {
            this.d_map = p_map;
            if (p_map == null) {
                Continent.resetNextContinentId();
            }
            return;
        }
        System.out.println("[Game]: Attempting to overwrite existing map object, declined. Please save the current map before editing another.");
    }

    /**
     * Getter for list of all the players
     *
     * @return list of existing players
     */
    public List<Player> getD_players() {
        if (d_players != null) {
            return d_players;
        }
        d_players = new ArrayList<Player>();
        return d_players;
    }

    public void setD_players(List<Player> p_players) {
        d_players = p_players;
    }

    /**
     * Finds player with a given name
     *
     * @param name name of player to fetch
     * @return Player object with given name if present, null otherwise
     */
    public Player getPlayer(String name) {
        if (d_players == null) {
            return null;
        }
        Player l_player = null;
        for (Player p : d_players) {
            if (p.getD_name().equals(name)) {
                l_player = p;
                break;
            }
        }
        return l_player;
    }

    public void addPlayer(Player player) {
        d_players.add(player);
    }

    public boolean addPlayer(String p_name) {
        Player l_player = getPlayer(p_name);
        if (l_player == null) {
            getD_players().add(new Player(p_name));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a player object from the players list
     *
     * @param p_player player to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean removePlayer(Player p_player) {
        if (d_players == null || p_player == null) {
            return false;
        }

        return d_players.remove(p_player);
    }

    /**
     * Remove a player with given name from the players list
     *
     * @param p_playerName name of player to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean removePlayer(String p_playerName) {
        Player l_playerToRemove = null;
        for (Player player : d_players) {
            if (p_playerName.equalsIgnoreCase(player.getD_name())) {
                l_playerToRemove = player;
                break;
            }
        }
        return removePlayer(l_playerToRemove);
    }

    /**
     * randomly assign each country in countries list to a single player
     */
    public void assignCountriesToPlayers() {
        int playersCount = getD_players().size();
        switch (playersCount) {
            case 0:
                System.out.println("[AssignCountries]: No players defined. Cannot assign countries.");
                break;

            case 1:
                getD_map().getD_countries().forEach(country -> d_players.get(0).receiveOwnershipForCountry(country));
                System.out.println("[AssignCountries]: Single player defined. Assigning all the countries to: " + d_players.get(0).getD_name());
                System.out.println("[AssignCountries]: Winner: " + d_players.get(0).getD_name());
                WarzoneArena.endGamePlay();
                break;

            default:
                Random randomNum = new Random();
                getD_map().getD_countries().forEach(country -> {
                    int randomPlayerIndex = randomNum.nextInt(playersCount);
                    Player randomPlayer = d_players.get(randomPlayerIndex);
                    if (randomPlayer.receiveOwnershipForCountry(country)) {
                        System.out.println("[Game]: Assigning country: " + country.getD_countryName() + " to: -- " + randomPlayer.getD_name());
                    }
                });
        }
    }

    /**
     * Return the owner name for a given country name
     * @param p_countryName name of country for which we need the owner name
     * @return Owner name if present, null otherwise
     */
    public String getOwnerNameForCountryName(String p_countryName) {
        String l_ownerName = null;
        for (Player l_player: getD_players()) {
            for (Country l_country: l_player.getD_ownedCountries()) {
                if (l_country.getD_countryName().equalsIgnoreCase(p_countryName)) {
                    l_ownerName = l_player.getD_name();
                    break;
                }
            }
            if (l_ownerName != null) {
                break;
            }
        }
        return l_ownerName;
    }

    /**
     * We set up the game play over here with proper description being given to user
     */
    public void setup() {
        System.out.println("--- Game setup ---");
        System.out.println("--- Enter commands to set up the map followed by 'commit' to begin game ---");
        System.out.println("--- At any point, you can give a command  'quit' to exit the game ---");
        String l_nextCommand = "";
        CommandParser l_parser = new CommandParser();

        while (!(l_nextCommand.equals("commit") || l_nextCommand.equals("quit"))) {
            Scanner commandReader = new Scanner(System.in);
            l_nextCommand = commandReader.nextLine();
            Command l_command = l_parser.parseCommandStatement(l_nextCommand);
            if (l_command != null) {
                l_command.execute();
                if (l_command.command == BaseCommand.AssignCountries) {
                    System.out.println("[Game] --- Ready for Issue Orders phase ---");
                    break;
                }
            } else if (l_nextCommand.equals("commit")) {
                System.out.println("[Game] --- Ready for next phase ---");
            } else if (l_nextCommand.equals("quit")) {
                System.out.println("[Game] --- Quitting game ---");
                WarzoneArena.endGamePlay();
            } else {
                System.out.println("[Undefined] Following command could not be understood: " + l_nextCommand);
            }
        }
    }
}
