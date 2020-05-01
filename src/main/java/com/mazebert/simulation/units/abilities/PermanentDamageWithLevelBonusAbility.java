package com.mazebert.simulation.units.abilities;

public strictfp class PermanentDamageWithLevelBonusAbility extends DamageWithLevelBonusAbility {
    public PermanentDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
