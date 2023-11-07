package main.java.phases;

import main.java.arena.Game;

/**
 * @author kevin on 2023-11-02
 */
public abstract class MainPlay extends Play {

    /**
     * Moves the game to 'End' phase which terminates the game
     */
    @Override
    public void endGame() {
        Game.sharedInstance().setD_gamePhase(new End());
    }
}
