package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.FollowPathBowlingBallAbility;

public strictfp class BowlingBallUnit extends Unit {

    private final FollowPathBowlingBallAbility followPathAbility = new FollowPathBowlingBallAbility();

    public BowlingBallUnit() {
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
}
