package main.java.arena;

import main.java.models.Player;
import main.java.orders.Order;

/**
 * A tactical game-play where the player objective is to capture as much territory as possible.
 * Player wins by controlling every territory possible.
 *
 * @author Kevin Wadera
 * @version 1.0
 */
public class WarzoneArena {

    /**
     * Global level boolean to terminate game
     */
    private static Boolean Is_Gameplay_On = true;


    /**
     * Global level game object
     */
    private static Game d_Game;

    /**
     * Global value for defining default reinforcement for each turn in the game
     */
    private static final int DEFAULT_REINFORCEMENT = 3;

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

            // assign reinforcement
            assignReinforcement();

            // Issue Orders phase
            issueOrdersPhase();

            // Execute Orders phase
            executeOrdersPhase();

            // Intentionally exit the game
            endGamePlay();
        }
    }

    private static void issueOrdersPhase() {
        System.out.println("--- Issuing Orders phase ---");
        boolean hasMoreOrders = true;
        while (hasMoreOrders) {
            hasMoreOrders = false;
            for (Player l_player: Game.sharedInstance().getD_players()) {
                if (l_player.canIssueOrder()) {
                    hasMoreOrders = true;
                    System.out.println("[GameEngine]: " + l_player.getD_name() + " has currently " + l_player.getD_assignedCountryUnits() + " army units left to deploy");
                    l_player.issue_order();
                }
            }
        }
    }

    private static void executeOrdersPhase() {
        System.out.println("--- Executing Orders Phase ---");
        boolean hasMoreOrders = true;
        while (hasMoreOrders) {
            hasMoreOrders = false;
            for (Player l_player: Game.sharedInstance().getD_players()) {
                Order l_order = l_player.nextorder();
                if (l_order != null) {
                    l_order.execute();
                    hasMoreOrders = true;
                }
            }
        }
    }

    private static void assignReinforcement() {
        System.out.println("--- Assigning Reinforcements phase ---");
        for (Player player: Game.sharedInstance().getD_players()) {
            // TODO: Check for ownership of continents, which should be added to DEFAULT_REINFORCEMENT
            player.setD_assignedCountryUnits(DEFAULT_REINFORCEMENT);
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
