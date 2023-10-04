package main.java.commands;

import main.java.arena.Game;

public class GamePlayerCommand extends Command{
    /**
     * Creates a new GamePlayer command with given params and/or any functionalities
     *
     * @param baseParams      represents any parameter to the basecommand
     * @param functionalities represents all the functionalities in the user input
     */
    public GamePlayerCommand(String[] baseParams, Functionality[] functionalities) {
        super(BaseCommand.GamePlayer, baseParams, functionalities);
    }

    /**
     * Executes the command provided by user. Performs validation for any error
     * {@inheritDoc }
     */
    @Override
    public void execute() {
        if (d_functionalities == null || d_functionalities.length == 0) {
            System.out.println("[GamePlayer]: No functionality provided. Expectation: '-add' or '-remove'");
            return;
        }
        Functionality l_function = d_functionalities[0];
        String l_playerName = null;
        switch (l_function.functionality) {
            case Add:
                if (l_function.functionalityParams == null || l_function.functionalityParams.length < 1) {
                    System.out.println("[GamePlayer]: Adding a player requires Player Name");
                    System.out.println("[GamePlayer]: Adding a player failed");
                    return;
                }
                l_playerName = l_function.functionalityParams[0];
                if(Game.sharedInstance().addPlayer(l_playerName)) {
                    System.out.println("[GamePlayer]: Adding a player successfull");
                } else {
                    System.out.println("[GamePlayer]: Adding a player failed");
                }
                break;
            case Remove:
                l_playerName = l_function.functionalityParams[0];
                if(Game.sharedInstance().removePlayer(l_playerName)) {
                    System.out.println("[GamePlayer]: Removing a player successfull");
                } else {
                    System.out.println("[GamePlayer]: Removing a player failed");
                }
                break;
            default:
                System.out.println("[EditContinent]: Functionality undefined. Expectation:  '-add' or '-remove'");
        }
    }
}
