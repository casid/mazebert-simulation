package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.units.creeps.effects.ReviveEffect;

public strictfp enum CreepModifier {
    Fast(5),
    Slow(5),
    Wisdom(0),
    Rich(0),
    Armor(10),
    Revive(10),
    ;

    private final int minRound;

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
