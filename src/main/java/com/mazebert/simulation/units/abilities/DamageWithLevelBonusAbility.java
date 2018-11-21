package com.mazebert.simulation.units.abilities;

public abstract strictfp class DamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public DamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAddedRelativeBaseDamage(amount);
    }
}