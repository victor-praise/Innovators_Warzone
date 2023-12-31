package main.java.arena;

import main.java.commands.BaseCommand;
import main.java.commands.Command;
import main.java.models.*;
import main.java.phases.*;
import main.java.services.MapService;
import main.java.strategy.HumanPlayerStrategy;
import main.java.strategy.PlayerStrategy;
import main.java.strategy.Strategy;
import main.java.utils.CommandParser;
import main.java.utils.logger.LogEntryBuffer;

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

    private Phase d_gamePhase;
    /**
     * private list of all the players
     */
    private List<Player> d_players; //need to know where will this list go

    /**
     * The current Map object in the game.
     */
    private Map d_map = null;

    /**
     * Global level boolean to terminate game
     */
    public static Boolean Is_Gameplay_On = true;

    /**
     * Informs the game that countries have been assigned to players
     */
    private static Boolean Player_Assignment_Complete = false;

    private GameMode gameMode = GameMode.Single;

    private Tournament tournament = null;

    private String d_gameWinner = "";

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
     * Return the current phase
     * @return current phase of game
     */
    public Phase getD_gamePhase() {
        return d_gamePhase;
    }

    /**
     * @param d_gamePhase phase to shift the game to
     */
    public void setD_gamePhase(Phase d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    /**
     * Return the current mode of the game
     * @return current gameMode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Sets the mode of the game
     * @param gameMode mode to set
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Returns the tournament object of our game
     * @return tournament object
     */
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Set the winner for last game;
     * @param d_gameWinner name of winner in last game
     */
    public void setD_gameWinner(String d_gameWinner) {
        this.d_gameWinner = d_gameWinner;
    }

    /**
     * Sets the tournament mode on for this game
     * @param tournament tournament object to set
     */
    public void setTournament(Tournament tournament) {
        if (tournament != null) {
            setGameMode(GameMode.Tournament);
            this.tournament = tournament;
            startTournament();
        }
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

    public boolean isMapEmpty() {
        return (d_map == null);
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

    public boolean addPlayer(Player p_player) {
        if (p_player == null) {
            return false;
        } else {
            getD_players().add(p_player);
            return true;
        }
    }

    public boolean addPlayer(String p_name) {
        Player l_player = getPlayer(p_name);
        if (l_player == null) {
            PlayerStrategy defaultStrategy = new HumanPlayerStrategy(Strategy.Human);
            getD_players().add(new Player(p_name, defaultStrategy));
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
        int l_playersCount = getD_players().size();
        String l_message;
        switch (l_playersCount) {
            case 0:
                l_message = "[AssignCountries]: No players defined. Cannot assign countries.";
                LogEntryBuffer.getInstance().log(l_message);
                System.out.println(l_message);
                break;

            case 1:
                getD_map().getD_countries().forEach(country -> d_players.get(0).receiveOwnershipForCountry(country));
                l_message = "[AssignCountries]: Single player defined. Assigning all the countries to: " + d_players.get(0).getD_name();
                LogEntryBuffer.getInstance().log(l_message);
                System.out.println("[AssignCountries]: Winner: " + d_players.get(0).getD_name());
                Game.endGamePlay();
                break;

            default:
                Random randomNum = new Random();
                getD_map().getD_countries().forEach(country -> {
                    int randomPlayerIndex = randomNum.nextInt(l_playersCount);
                    Player randomPlayer = d_players.get(randomPlayerIndex);
                    if (randomPlayer.receiveOwnershipForCountry(country)) {
                        LogEntryBuffer.getInstance().log("[Game]: Assigning country: " + country.getD_countryName() + " to: -- " + randomPlayer.getD_name());
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
    public void start() {
        LogEntryBuffer.getInstance().log("===== Game Startup =====" + "\n");
        System.out.println("--- At any point, you can give a command  'quit' to exit the game ---");
        String l_nextCommand = "";
        CommandParser l_parser = new CommandParser();
        setD_gamePhase(new Preload());

        Scanner commandReader = new Scanner(System.in);
        l_nextCommand = commandReader.nextLine();
        Command l_command = l_parser.parseCommandStatement(l_nextCommand);
        while (l_command.d_command != BaseCommand.Quit && Is_Gameplay_On) {
            d_gamePhase.executeCommand(l_command);
            if (Player_Assignment_Complete) {
                // We don't need anymore inputs
                break;
            }
            l_command = l_parser.parseCommandStatement(commandReader.nextLine());
        }


        while (Is_Gameplay_On) {
            d_gamePhase.next();
        }
    }

    /**
     * Begin tournament mode.
     */
    public void startTournament() {
        System.out.println(" --- TOURNAMENT-MODE BEGINS ---");
        tournament.createPlayers();
        int mapIndex = 0;

        for (String map: tournament.getMapNames()) {
            for (int gameIndex = 0; gameIndex < tournament.getNumberOfGames(); gameIndex++) {
                Is_Gameplay_On = true;
                System.out.println("\n\n\n ***** Round " + (gameIndex + 1) + " for map: " + map + " begins \n\n");
                loadMap(map);
                assignCountriesToPlayers();

                setD_gamePhase(new Reinforcement());
                while (Is_Gameplay_On) {
                    d_gamePhase.next();
                }

                tournament.results[mapIndex][gameIndex] = d_gameWinner;

                System.out.println("\n\n\n  ***** Round " + (gameIndex + 1) + " for map: " + map + " ends.\n\n");
                resetGame();
            }
            mapIndex++;
        }

        endTournament();
    }

    /**
     * End tournament mode.
     */
    public void endTournament() {
        // Intentionally ending game.
        Player_Assignment_Complete = true;
        System.out.println(" --- TOURNAMENT-MODE ENDS ---");
        tournament.displayResults();
    }

    public void playerAssignmentComplete() {
        Player_Assignment_Complete = true;
    }

    /**
     * Call to this method should be made when the game needs to be terminated
     */
    // To be called when a single player controls entire board
    public static void endGamePlay() {
        Is_Gameplay_On = false;
        LogEntryBuffer.getInstance().log("==== Game Ended ====");
    }

    /**
     * loads map with a given file name
     * @param l_filename name of the file to load
     */
    private void loadMap(String l_filename) {
        MapService mapService = new MapService();
        if (!mapService.attemptLoadMapWithFileName(l_filename)) {
            System.out.println("[LoadMapCommand]: file does not exist, please provide the name of a file that exists ");
        }
    }

    private void resetGame() {
        d_map = null;
        setD_map(null);
        setD_gamePhase(null);
        for (Player player: getD_players()) {
            player.reset();
        }
        Reinforcement.TURN_NUMBER = 0;
    }
}
