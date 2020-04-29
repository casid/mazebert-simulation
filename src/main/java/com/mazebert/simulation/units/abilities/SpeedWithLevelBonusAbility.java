package com.mazebert.simulation.units.abilities;

public abstract strictfp class SpeedWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public SpeedWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAttackSpeed(amount);
    }

    public String getTitle() {
        return "More speed!";
    }

    @Override
    protected String getAttributeName() {
        return "attack speed";
    }
}
