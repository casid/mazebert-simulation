package com.mazebert.simulation.units.abilities;

public strictfp class PermanentLuckWithLevelBonusAbility extends LuckWithLevelBonusAbility {
    public PermanentLuckWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    public String getDescription() {
        return "The carrier's luck is permanently increased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
