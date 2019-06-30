package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.creeps.effects.ReviveEffect;

public strictfp enum CreepModifier {
    Fast(5),
    Slow(5),
    Wisdom(0),
    Rich(0),
    Armor(10),
    Revive(10),
    Steady(30)
    ;

    private static final CreepModifier[] STANDARD = {Fast, Slow, Wisdom, Rich, Armor, Revive};
    private static final CreepModifier[] DAWN_OF_LIGHT = {Fast, Slow, Wisdom, Rich, Armor, Revive, Steady};

    private final int minRound;

    public static CreepModifier[] getValues() {
        if (Sim.context().version >= Sim.vDoL) {
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
                creep.setHealth(creep.getMaxHealth());
                break;
            case Slow:
                creep.setSpeedModifier(creep.getSpeedModifier() / 1.5f);
                creep.setMaxHealth(creep.getMaxHealth() * 1.5);
                creep.setHealth(creep.getMaxHealth());
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
                creep.setHealth(creep.getMaxHealth());
                creep.addAbility(new ReviveEffect());
                break;
            case Steady:
                creep.setMaxHealth(creep.getMaxHealth() * 0.7);
                creep.setHealth(creep.getMaxHealth());
                creep.setSteady(true);
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
    public boolean isPossible(int round) {
        return round >= minRound;
    }
}
