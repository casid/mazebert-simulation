package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.maps.MapGrid;
import com.mazebert.simulation.minigames.BowlingGame;
import com.mazebert.simulation.units.abilities.CooldownActiveAbility;

import static com.mazebert.simulation.units.items.BowlingBall.BALL_COOLDOWN;
import static com.mazebert.simulation.units.items.BowlingBall.DEATH_CHANCE;

public strictfp class BowlingBallRollAbility extends CooldownActiveAbility {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private BowlingGame game;

    @Override
    public float getCooldown() {
        return BALL_COOLDOWN;
    }

    @Override
    public void activate() {
        BowlingBallUnit ball = new BowlingBallUnit(getUnit(), game);
        ball.setWizard(getUnit().getWizard());

        Map map = gameGateway.getMap();
        FollowPathResult result = FollowPath.findClosestPointOnPath(getUnit().getX(), getUnit().getY(), map.getGroundPath());

        Path path = map.getGrid().findPath(StrictMath.round(result.px), StrictMath.round(result.py), map.getStartWaypoint().x, map.getStartWaypoint().y, MapGrid.WALKABLE);
        ball.setPath(path);

        unitGateway.addUnit(ball);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.soundNotification("sounds/bowling-roll.mp3");
        }

        startCooldown();
    }

    @Override
    protected void onAbilityReady() {
        super.onAbilityReady();
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Ready to roll!", BowlingBall.NOTIFICATION_COLOR);
        }
    }

    public void setGame(BowlingGame game) {
        this.game = game;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Roll ball (Active ability)";
    }

    @Override
    public String getDescription() {
        return "You can roll the ball every " + format.seconds(BALL_COOLDOWN) + ". Ground units hit by the ball have a " + format.percent(DEATH_CHANCE) + "% chance to die instantly. Bosses deflect the ball to the gutter.";
    }
}
