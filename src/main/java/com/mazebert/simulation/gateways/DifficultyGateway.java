package com.mazebert.simulation.gateways;

import com.mazebert.simulation.Difficulty;

public strictfp class DifficultyGateway {
    private Difficulty difficulty = Difficulty.Easy;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
