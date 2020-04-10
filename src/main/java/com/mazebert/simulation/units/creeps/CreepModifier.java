package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.units.creeps.effects.GhostEffect;
import com.mazebert.simulation.units.creeps.effects.ReviveEffect;
import com.mazebert.simulation.units.creeps.effects.UnionEffect;

public strictfp enum CreepModifier {
    Fast(5),
    Slow(5),
    Wisdom(0),
    Rich(0),
    Armor(10),
    Revive(10),
    Steady(30),
    Union(20),
    Loot(10),
    Ghost(50),
    ;

    private static final CreepModifier[] STANDARD =      {Fast, Slow, Wisdom, Rich, Armor, Revive};
    private static final CreepModifier[] DAWN_OF_LIGHT = {Fast, Slow, Wisdom, Rich, Armor, Revive, Steady, Union, Loot, Ghost};

    private final int minRound;

    public static CreepModifier[] getValues() {
        if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    CreepModifier(int minRound) {
        this.minRound = minRound;
    }

    public void apply(Creep creep) {
        switch (this) {
            case Fast:
                creep.setSpeedModifier(creep.getSpeedModifier() * 1.5f);
                creep.setMaxHealth(creep.getMaxHealth() / 1.5);
                break;
            case Slow:
                creep.setSpeedModifier(creep.getSpeedModifier() / 1.5f);
                creep.setMaxHealth(creep.getMaxHealth() * 1.5);
                break;
            case Wisdom:
                creep.setExperienceModifier(creep.getExperienceModifier() * 1.6f);
                break;
            case Rich:
                creep.setGold((int)(creep.getGold() * 1.6f));
                break;
            case Armor:
                creep.addArmor(30);
                break;
            case Revive:
                creep.setMaxHealth(creep.getMaxHealth() / 2);
                creep.addAbility(new ReviveEffect());
                break;
            case Steady:
                creep.setMaxHealth(creep.getMaxHealth() * 0.7);
                creep.setSteady(true);
                break;
            case Union:
                if (Sim.context().version >= Sim.vDoLEnd) {
                    creep.setMaxHealth(creep.getMaxHealth() * creep.getWave().creepCount);
                } else {
                    creep.setMaxHealth(creep.getMaxHealth() * creep.getWave().creepCount * Sim.context().playerGateway.getPlayerCount());
                }
                creep.addAbility(new UnionEffect());
                break;
            case Loot:
                creep.setDropChance(creep.getDropChance() * 1.2f);
                if (Sim.context().version >= Sim.vDoLEndBeta3 && creep.getWave().creepCount == 1) {
                    creep.setMinDrops(creep.getMinDrops() + 1);
                    creep.setMaxDrops(creep.getMaxDrops() + 1);
                }
                break;
            case Ghost:
                creep.setMaxHealth(creep.getMaxHealth() * GhostEffect.CHANCE_TO_MISS);
                creep.addAbility(new GhostEffect());
                break;
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean isCompatible(CreepModifier other) {
        if (this == other) {
            return false;
        }

        if (this == Fast && other == Slow) {
            return false;
        }

        if (this == Slow && other == Fast) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isPossible(Wave wave) {
        if (wave.round < minRound) {
            return false;
        }

        if (this == Union && wave.creepCount <= 1) {
            if (Sim.context().version >= Sim.vDoLEndBeta2) {
                return false;
            } else {
                return Sim.context().playerGateway.getPlayerCount() > 1;
            }
        }
        return true;
    }
}
