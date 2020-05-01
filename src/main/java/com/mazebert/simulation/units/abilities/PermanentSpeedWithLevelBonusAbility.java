package com.mazebert.simulation.units.abilities;

public abstract strictfp class PermanentSpeedWithLevelBonusAbility extends SpeedWithLevelBonusAbility {
    public PermanentSpeedWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
