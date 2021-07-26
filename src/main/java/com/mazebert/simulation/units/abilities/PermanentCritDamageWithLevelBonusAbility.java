package com.mazebert.simulation.units.abilities;

public strictfp class PermanentCritDamageWithLevelBonusAbility extends CritDamageWithLevelBonusAbility {
    public PermanentCritDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
