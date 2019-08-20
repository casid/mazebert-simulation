package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DungeonDoorAbility extends Ability<Tower> implements OnRoundStartedListener {
    public static final float chance = 0.33f;

    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onRoundStarted.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        Sim.context().simulationListeners.onRoundStarted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onRoundStarted(Wave wave) {
        if (getUnit().isAbilityTriggered(chance)) {
            FollowPathResult result = FollowPath.findClosestPointOnPath(getUnit().getX(), getUnit().getY(), gameGateway.getMap().getGroundPath());
            Sim.context().waveSpawner.spawnTreasureGoblin(getUnit().getWizard(), result.pathIndex, result.px, result.py);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Treasure goblin";
    }

    @Override
    public String getDescription() {
        return "Every round there is a " + format.percent(chance) + "% chance that a treasure goblin escapes the dungeon. The goblin spawns next to the carrier.";
    }
}
