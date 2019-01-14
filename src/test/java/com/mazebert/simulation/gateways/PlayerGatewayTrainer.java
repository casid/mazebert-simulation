package com.mazebert.simulation.gateways;

public class PlayerGatewayTrainer implements PlayerGateway {

    private int playerCount = 1;

    @Override
    public int getSimulationPlayerId() {
        return 1;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    public void givenPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}