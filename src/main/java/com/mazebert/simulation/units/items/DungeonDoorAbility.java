package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DungeonDoorAbility extends CooldownAbility<Tower> {
    public static final int range = 1;
    public static final int cooldown = 60 * 3;
    public static final float chance = 0.33f;
    public static final float chanceBonus = 0.004f;

    private final WaveSpawner waveSpawner = Sim.context().waveSpawner;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    protected float getCooldown() {
        float attackSpeedModifier = getUnit().getAttackSpeedModifier();
        if (attackSpeedModifier <= 0.0) {
            return cooldown;
        }

        return cooldown / attackSpeedModifier;
    }

    @Override
    protected boolean onCooldownReached() {
        if (getUnit().isAbilityTriggered(chance + getUnit().getLevel() * chanceBonus)) {
            int index = findFirstReachableIndexOnPath(gameGateway.getMap().getGroundPath());
            if (index >= 0) {
                waveSpawner.spawnTreasureGoblin(getUnit().getWizard(), index);
            }
        }

        return true;
    }

    private int findFirstReachableIndexOnPath(Path path) {
        int pathLength = path.size();
        float x = getUnit().getX();
        float y = getUnit().getY();
        for (int i = 0; i < pathLength; ++i) {
            float dx = path.getX(i) - x;
            float dy = path.getY(i) - y;

            if (dx < 0.0f) dx *= -1.0f;
            if (dy < 0.0f) dy *= -1.0f;

            if (dx <= range && dy <= range) {
                return i;
            }
        }

        return -1;
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
        return "Every " + (cooldown / 60) + " minutes there is a " + format.percent(chance) + "% chance that a treasure goblin escapes the dungeon. The goblin spawns within " + range + " range of the carrier.";
    }
}
