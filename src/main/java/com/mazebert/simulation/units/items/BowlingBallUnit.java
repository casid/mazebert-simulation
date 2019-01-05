package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.minigames.BowlingGame;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.FollowPathBowlingBallAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BowlingBallUnit extends Unit {

    private final Tower tower;
    private final BowlingGame game;
    private final FollowPathBowlingBallAbility followPathAbility = new FollowPathBowlingBallAbility();

    public BowlingBallUnit(Tower tower, BowlingGame game) {
        this.tower = tower;
        this.game = game;
        addAbility(followPathAbility);
    }

    @Override
    public String getModelId() {
        return "bowling-ball";
    }

    public void setPath(Path path) {
        followPathAbility.setPath(path);
    }

    @SuppressWarnings("unused") // Used by client
    public FollowPathResult predictRoll(float x, float y, float dt, FollowPathResult result) {
        return followPathAbility.predict(x, y, dt, result);
    }

    public Tower getTower() {
        return tower;
    }

    public BowlingGame getGame() {
        return game;
    }
}
