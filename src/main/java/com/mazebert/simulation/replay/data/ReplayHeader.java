package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.Sim;

public strictfp class ReplayHeader {
    public String version = Sim.version;
    public int playerId;
    public int playerCount;
}
