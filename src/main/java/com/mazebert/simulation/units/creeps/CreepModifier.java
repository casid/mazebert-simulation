package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.units.creeps.effects.SecondChanceEffect;

public strictfp enum CreepModifier {
    Fast,
    Slow,
    Wisdom,
    Rich,
    Armor,
    SecondChance,
    ;

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
            case SecondChance:
                creep.setMaxHealth(creep.getMaxHealth() / 2);
                creep.setHealth(creep.getMaxHealth());
                creep.addAbility(new SecondChanceEffect());
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
}
