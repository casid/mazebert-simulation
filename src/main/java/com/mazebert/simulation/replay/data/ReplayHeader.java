package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.Sim;

public strictfp class ReplayHeader {
    public static final int FORMAT_IDENTIFIER = 0x6d626773; // m b g s

    public int version = Sim.version;
    public int playerId;
    public int playerCount;
}
