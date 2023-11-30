package main.java.arena;

import main.java.utils.logger.LogEntryBuffer;
import main.java.utils.logger.LogWriter;

/**
 * A tactical game-play where the player objective is to capture as much territory as possible.
 * Player wins by controlling every territory possible.
 *
 * @author Kevin Wadera
 * @version 1.0
 */
public class WarzoneArena {

    /**
     * Global level game object
     */
    private static Game d_Game;

    /**
     * Global level Logger (Observable)
     */
    private static LogEntryBuffer d_Logger;
    /**

     * Main entry point of the game.
     *
     * @param args Any command line arguments separated by a space
     */
    public static void main(String[] args) {
        // Gameplay setup goes here
        startGame();
    }

    /**
     * All the game setup parameters will go here
     */
    private static void startGame() {

        d_Game = Game.sharedInstance();
        d_Logger = LogEntryBuffer.getInstance();
        d_Logger.addObserver(new LogWriter());

        d_Game.start();
    }
}
