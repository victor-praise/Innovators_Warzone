package main.java.models;

import java.io.Serializable;

/**
 *  Diplomacy model handles diplomacy command.
 *  @author mohammadalshariar
 */
public class Diplomacy implements Serializable {
    /**
     * Player who are negotiating.
     */
    Player d_playerWhoNegotiate;
    /**
     * Player with whom negotiate are established
     */
    String d_playerAcceptedTheNegotiation;

    /**
     * Execution log records.
     */
    String d_executableOrderLog;

    public Diplomacy(Player d_playerWhoNegotiate, String d_playerAcceptedTheNegotiation) {
        this.d_playerWhoNegotiate = d_playerWhoNegotiate;
        this.d_playerAcceptedTheNegotiation = d_playerAcceptedTheNegotiation;
    }
}
