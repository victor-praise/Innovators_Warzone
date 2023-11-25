package main.java.models;

import main.java.arena.Game;
import main.java.strategy.Strategy;
import main.java.strategy.StrategyFactory;

/**
 * Contains the information about the tournament if the game is running in tournament mode
 *
 * @author kevin on 2023-11-24
 * @version 1.3
 */
public class Tournament {
    String[] mapNames;
    Strategy[] playerStrategies;
    int numberOfGames;
    int maxNumberOfTurns;

    public Tournament() {
        this(null, null, 0, 0);
    }

    public Tournament(String[] mapNames, Strategy[] playerStrategies, int numberOfGames, int maxNumberOfTurns) {
        this.mapNames = mapNames;
        this.playerStrategies = playerStrategies;
        this.numberOfGames = numberOfGames;
        this.maxNumberOfTurns = maxNumberOfTurns;
    }

    public String[] getMapNames() {
        return mapNames;
    }

    public void setMapNames(String[] mapNames) {
        this.mapNames = mapNames;
    }

    public Strategy[] getPlayerStrategies() {
        return playerStrategies;
    }

    public void setPlayerStrategies(Strategy[] playerStrategies) {
        this.playerStrategies = playerStrategies;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getMaxNumberOfTurns() {
        return maxNumberOfTurns;
    }

    public void setMaxNumberOfTurns(int maxNumberOfTurns) {
        this.maxNumberOfTurns = maxNumberOfTurns;
    }

    public void createPlayers() {
        String playerNamePrefix = "Player";
        int playerIndex = 1;
        for (Strategy strategy: playerStrategies) {
            String l_name = playerNamePrefix + playerIndex++;
            Player nextPlayer = new Player(l_name, StrategyFactory.generateStrategyObject(strategy));
            System.out.println("Created new player with name: " + l_name + " and strategy: " + strategy.toString());
            Game.sharedInstance().addPlayer(nextPlayer);
        }
    }
}
