package main.java.models;

import main.java.arena.Game;
import main.java.strategy.Strategy;
import main.java.strategy.StrategyFactory;
import main.java.utils.Constants;

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

    public String[][] results;

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
        Constants.MAX_TURNS_ALLOWED = maxNumberOfTurns;
    }

    public void createPlayers() {
        this.results = new String[mapNames.length][numberOfGames];
        String playerNamePrefix = "Player";
        int playerIndex = 1;
        for (Strategy strategy: playerStrategies) {
            String l_name = playerNamePrefix + playerIndex++;
            Player nextPlayer = new Player(l_name, StrategyFactory.generateStrategyObject(strategy));
            System.out.println("Created new player with name: " + l_name + " and strategy: " + strategy.toString());
            Game.sharedInstance().addPlayer(nextPlayer);
        }
    }

    public void displayResults() {
        System.out.println("\n\n **************  Final Results  **************\n\n");
        System.out.print("\t\t\t\t M:  \t");
        boolean isFirst = true;
        for (String mapName: mapNames) {
            String label = mapName.substring(0,1).toUpperCase() + mapName.substring(1).toLowerCase();
            System.out.print((isFirst ? "" : ", ") + label);
            if (isFirst) {
                isFirst = false;
            }
        }
        System.out.println();

        System.out.print("\t\t\t\t P:  \t");
        isFirst = true;
        for (Strategy strategy: playerStrategies) {
            String label = strategy.d_label.substring(0,1).toUpperCase() + strategy.d_label.substring(1);
            System.out.print((isFirst ? "" : ", ") + label);
            if (isFirst) {
                isFirst = false;
            }
        }
        System.out.println();

        System.out.println("\t\t\t\t G:  \t" + numberOfGames);
        System.out.println("\t\t\t\t D:  \t" + maxNumberOfTurns);
        System.out.println();
        System.out.println();
        System.out.println();

        for (int index = 0; index <= mapNames.length; index++) {
            if (index == 0) {
                System.out.print("*\t\t\t\t | \t");
                for (int gameIndex = 0; gameIndex < numberOfGames; gameIndex++) {
                    System.out.print("\t\t Game " + (gameIndex + 1) + "\t\t\t\t |  " );
                }
                System.out.println("*");
            } else {
                String mapName = mapNames[index - 1];
                String[] mapWinners = results[index - 1];
                System.out.print("*\t" + mapName + "\t\t | ");
                for (int gameIndex = 0; gameIndex < numberOfGames; gameIndex++) {
                    String label = mapWinners[gameIndex];
                    String tabsAfter = "\t\t | ";
                    String tabsBefore = "\t\t ";
                    if (label.equals("DRAW")) {
                        tabsAfter = "\t\t\t\t |  ";
                        tabsBefore = "\t\t\t ";
                    } else {
                        label = label.substring(0,1).toUpperCase() + label.substring(1);
                    }
                    System.out.print(tabsBefore + label + tabsAfter);
                }
                System.out.println("*");
            }
        }

        System.out.println("\n\n **************  Final Results  **************\n\n");

    }
}
