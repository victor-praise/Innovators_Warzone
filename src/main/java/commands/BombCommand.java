package main.java.commands;

import main.java.arena.Game;
import main.java.models.Country;
import main.java.models.Player;
import main.java.orders.BombOrder;
import main.java.utils.logger.LogEntryBuffer;

import java.util.List;

public class BombCommand extends PlayerOrderCommand {
    /**
     * Creates a new Order command for a given player
     *
     * @param p_player   player who issued the order
     * @param baseParams represents any parameter to the basecommand
     */
    public BombCommand(Player p_player, String[] baseParams) {
        super(p_player, BaseCommand.Bomb, baseParams, null);
    }

    /**
     * Validates the Bomb order issued by the player, and if valid, inserts into the players order queue
     */
    @Override
    public void execute() {
        String l_message;
        if (d_baseParams == null || d_baseParams.length < 1) {
            l_message = "[Bomb]: Bomb order requires one parameter. [1] Target Country's name";
            LogEntryBuffer.getInstance().log(l_message);
            System.out.println(l_message);
            return;
        }

        String l_countryName = d_baseParams[0];
        Country l_targetCountry = Game.sharedInstance().getD_map().getCountry(l_countryName);
        List<Country> l_ownedCountries = this.d_issuingPlayer.getD_ownedCountries();
        if (l_targetCountry == null) {
            l_message = "[Bomb]: Target Country does not exist";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        if (!this.d_issuingPlayer.hasSpecialCardOfType("bomb")) {
            l_message = "[Bomb]: Player " + this.d_issuingPlayer.getD_name() + " does not have a bomb card";
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        boolean l_isNeighbour = false;
        for (Country country : l_ownedCountries) {
            if (country.hasAdjacentCountry(l_targetCountry)) {
                l_isNeighbour = true;
                break;
            }
        }

        if (!l_isNeighbour) {
            l_message = "[Bomb]: Country " + l_countryName + " is not adjacent to any country owned by " + this.d_issuingPlayer.getD_name();
            LogEntryBuffer.getInstance().log(l_message);
            return;
        }

        d_issuingPlayer.appendOrderToList(new BombOrder(d_issuingPlayer, l_targetCountry));
    }
}
