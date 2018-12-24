package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WeddingRingSystem {
    public static final int SECONDS_FOR_MARRIAGE = 20;

    private final WeddingRingPlayerSystem[] playerSystems;

    public WeddingRingSystem() {
        playerSystems = new WeddingRingPlayerSystem[Sim.context().playerGateway.getPlayerCount()];
        for (int i = 0; i < playerSystems.length; ++i) {
            playerSystems[i] = new WeddingRingPlayerSystem();
        }
    }

    public void setTower(int playerId, int index, Tower tower) {
        int playerIndex = playerId - 1;
        playerSystems[playerIndex].setTower(index, tower);
    }
}
