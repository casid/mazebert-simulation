package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.maps.MapGrid;
import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class BowlingBallRollAbility extends ActiveAbility {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    public float getCooldown() {
        return 1;
    }

    @Override
    public void activate() {
        BowlingBallUnit ball = new BowlingBallUnit();
        ball.setWizard(getUnit().getWizard());

        Map map = gameGateway.getMap();
        FollowPathResult result = FollowPath.findClosestPointOnPath(getUnit().getX(), getUnit().getY(), map.getGroundPath());

        Path path = map.getGrid().findPath(StrictMath.round(result.px), StrictMath.round(result.py), map.getStartWaypoint().x, map.getStartWaypoint().y, MapGrid.WALKABLE);
        ball.setPath(path);

        unitGateway.addUnit(ball);
    }
}
