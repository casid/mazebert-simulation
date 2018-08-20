package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Game;
import org.jusecase.inject.Component;

@Component
public strictfp class GameGateway {
    private final Game game = new Game();

    public Game getGame() {
        return game;
    }
}
