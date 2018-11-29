package com.mazebert.simulation.units.abilities;

public strictfp class PermanentDamageWithLevelBonusAbility extends DamageWithLevelBonusAbility {
    public PermanentDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    public String getDescription() {
        return "The carrier's damage is permanently increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
