package com.mazebert.simulation.units.abilities;

public strictfp class PermanentCritChanceWithLevelBonusAbility extends CritChanceWithLevelBonusAbility {
    public PermanentCritChanceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
