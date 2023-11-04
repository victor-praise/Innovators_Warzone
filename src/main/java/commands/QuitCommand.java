package main.java.commands;

import main.java.arena.Game;
import main.java.phases.End;

/**
 * When a player issues 'quit' command, the game will terminate
 * @author kevin on 2023-11-04
 */
public class QuitCommand extends Command {
    /**
     * Creates a Quit Command object to end the game
     *
     */
    public QuitCommand() {
        super(BaseCommand.Quit, null, null);
    }

    /**
     *  Moves the game to 'End' phase to terminate the game
     */
    public void execute() {
        Game.sharedInstance().setD_gamePhase(new End());
    }
}
