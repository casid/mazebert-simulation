package com.mazebert.simulation.replay.data;

public strictfp class ReplayHeader {
    public static final int FORMAT_IDENTIFIER = 0x7367626d; // m b g s

    public int version;
    public boolean season;
    public int playerId;
    public int playerCount;
}
