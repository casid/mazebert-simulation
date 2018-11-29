package com.mazebert.simulation.units.abilities;

public abstract strictfp class PermanentSpeedWithLevelBonusAbility extends SpeedWithLevelBonusAbility {
    public PermanentSpeedWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    public String getDescription() {
        return "The attack speed of the carrier is permanently increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
