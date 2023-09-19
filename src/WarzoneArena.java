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
 * @author Kevin Wadera
 */

// A tactical game-play where the player objective is to capture as much territory as possible.
// Player wins by controlling every territory possible.
public class WarzoneArena {
    public  static Boolean IS_GAMEPLAY_ON = true;
    public static void main(String[] args) {
        // Gameplay setup goes here

        // Gameplay begins here
        while (IS_GAMEPLAY_ON) {
            System.out.print("Warp to Warzone!");
            // Intentionally exit the game
            endGamePlay();
        }
    }

    // To be called when a single player controls entire board
    public static void endGamePlay() {
        IS_GAMEPLAY_ON = false;
    }
}
