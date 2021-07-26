package com.mazebert.simulation.units.abilities;

public strictfp class PermanentItemChanceWithLevelBonusAbility extends ItemChanceWithLevelBonusAbility {
    public PermanentItemChanceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
