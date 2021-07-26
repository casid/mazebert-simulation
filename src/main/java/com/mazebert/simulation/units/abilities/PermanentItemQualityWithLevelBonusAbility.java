package com.mazebert.simulation.units.abilities;

public strictfp class PermanentItemQualityWithLevelBonusAbility extends ItemQualityWithLevelBonusAbility {
    public PermanentItemQualityWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
