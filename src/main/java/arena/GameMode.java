package main.java.arena;

/**
 * Tells the mode in which current game is being executed
 * @author kevin on 2023-11-24
 * @version 1.3
 */
public enum GameMode {
    /**
     * User chooses the number and behavior of players
     */
    Single,
    /**
     * The tournament should proceeds without any user interaction and show the results of the tournament at the end.
     */
    Tournament;
}
