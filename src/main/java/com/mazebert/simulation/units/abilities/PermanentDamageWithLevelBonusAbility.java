package com.mazebert.simulation.units.abilities;

public class PermanentDamageWithLevelBonusAbility extends DamageWithLevelBonusAbility {
    public PermanentDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    public String getDescription() {
        return "The carrier's damage is permanently increased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
