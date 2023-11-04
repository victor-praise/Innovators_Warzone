package main.java.arena;

/**
 * A tactical game-play where the player objective is to capture as much territory as possible.
 * Player wins by controlling every territory possible.
 *
 * @author Kevin Wadera
 * @version 1.0
 */
public class WarzoneArena {

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
        Game d_Game = Game.sharedInstance();
        d_Game.start();
    }
}
