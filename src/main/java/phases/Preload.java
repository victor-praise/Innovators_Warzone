package main.java.phases;

import main.java.arena.Game;
import main.java.commands.Functionality;
import main.java.utils.logger.LogEntryBuffer;

/**
 * @author kevin on 2023-10-31
 * @author victor
 */
public class Preload extends Edit {

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void loadMap(String[] p_baseParams, Functionality[] p_functionalities) {
        super.loadMap(p_baseParams, p_functionalities);
        System.out.println("[PreLoad]: Changing state to post load");
        LogEntryBuffer.getInstance().log("\n" + "[PreLoad]: Changing state to post load");
        Game.sharedInstance().setD_gamePhase(new PostLoad());
    }

    /**
     * @param p_baseParams
     * @param p_functionalities
     */
    @Override
    public void saveMap(String[] p_baseParams, Functionality[] p_functionalities) {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    public void next() {
        LogEntryBuffer.getInstance().log("[PreLoad]: must load map, currently in pre load phase");
        System.out.println("[PreLoad]: must load map, currently in pre load phase");
    }

    /**
     *
     */
    @Override
    public void printInvalidCommandMessage() {
        super.printInvalidCommandMessage();
        System.out.println("Valid commands in state " + this.getClass().getSimpleName() + " are: ");
        System.out.println("1. loadmap [filename]");
        System.out.println(" --- ");
    }
}
