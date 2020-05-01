package com.mazebert.simulation.units.abilities;

public strictfp class PermanentLuckWithLevelBonusAbility extends LuckWithLevelBonusAbility {
    public PermanentLuckWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
