package com.mazebert.simulation.units.items;

import com.mazebert.simulation.minigames.BowlingGame;
import com.mazebert.simulation.minigames.BowlingGameFormatter;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BowlingBallGameAbility extends Ability<Tower> {
    private final BowlingGameFormatter formatter = new BowlingGameFormatter();
    private BowlingGame game;

    public void setGame(BowlingGame game) {
        this.game = game;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Current Game";
    }

    @Override
    public String getDescription() {
        return formatter.formatFrames(game) + "\nScore: " + game.getScore();
    }
}
