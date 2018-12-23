package com.mazebert.simulation.units.creeps;

public strictfp enum CreepModifier {
    Fast,
    Slow,
    Wisdom,
    Rich,
    Armor,
    SecondChance,
    ;

    @SuppressWarnings("RedundantIfStatement")
    public boolean isCompatible(CreepModifier other) {
        if (this == Fast && other == Slow) {
            return false;
        }

        if (this == Slow && other == Fast) {
            return false;
        }

        return true;
    }
}
