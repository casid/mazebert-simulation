package com.mazebert.simulation.units.abilities;

public abstract strictfp class CritChanceWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public CritChanceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addCritChance(amount);
    }
}
