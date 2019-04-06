package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DungeonDoorAbility extends CooldownAbility<Tower> {
    public static final int cooldown = 60 * 3;
    public static final int minCooldown = 30;
    public static final float chance = 0.33f;
    public static final float chanceBonus = 0.004f;

    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final int version = Sim.context().version;

    @Override
    protected float getCooldown() {
        float attackSpeedModifier = getUnit().getAttackSpeedModifier();
        if (attackSpeedModifier <= 0.0) {
            return cooldown;
        }

        if (version > Sim.v11) {
            return StrictMath.max(minCooldown, cooldown / attackSpeedModifier);
        } else {
            return cooldown / attackSpeedModifier;
        }
    }

    @Override
    protected boolean onCooldownReached() {
        if (getUnit().isAbilityTriggered(chance + getUnit().getLevel() * chanceBonus)) {
            FollowPathResult result = FollowPath.findClosestPointOnPath(getUnit().getX(), getUnit().getY(), gameGateway.getMap().getGroundPath());
            Sim.context().waveSpawner.spawnTreasureGoblin(getUnit().getWizard(), result.pathIndex, result.px, result.py);
        }

        return true;
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
        return "Every " + (cooldown / 60) + " minutes there is a " + format.percent(chance) + "% chance that a treasure goblin escapes the dungeon. The goblin spawns next to the carrier.";
    }
}
