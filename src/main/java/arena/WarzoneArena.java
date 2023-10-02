package main.java.arena;

/*
 * Main entry point for the game nick-named `WARZONE`
 * This project is part of SOEN-6441 class, batch Fall-2023, headed by Dr. Amin Ranj Bar
 * The project members of team W14(Innovators) include:
 *  - Angad Virdi
 *  - Karansinh Matroja
 *  - Kevin Wadera
 *  - Mohammad Al-Shariar
 *  - Sadiq Ali Shaik
 *  - Victor Nwatu
 */

/**
 * A tactical game-play where the player objective is to capture as much territory as possible.
 * Player wins by controlling every territory possible.
 *
 * @author Kevin Wadera
 * @version 1.0
 */
public class WarzoneArena {

    private static Boolean Is_Gameplay_On = true;
    private static Game d_Game;

    /**
     * Main entry point of the game.
     *
     * @param args Any command line arguments separated by a space
     */
    public static void main(String[] args) {
        // Gameplay setup goes here
        setupGame();

        // Gameplay begins here
        while (Is_Gameplay_On) {
            System.out.println("Warp to Warzone!");
            // auto build check
            System.out.print("auto build completed! --- Git Guardian");
            // Intentionally exit the game
            endGamePlay();
        }
    }

    /**
     * Call to this method should be made when the game needs to be terminated
     */
    // To be called when a single player controls entire board
    public static void endGamePlay() {
        Is_Gameplay_On = false;
    }

    /**
     * All the game setup parameters will go here
     */
    private static void setupGame() {
        d_Game = Game.sharedInstance();
        d_Game.setup();
    }
}
