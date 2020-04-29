package com.mazebert.simulation.units.abilities;

public strictfp class GoldWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public GoldWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addGoldModifer(amount);
    }

    public String getTitle() {
        return "More " + getCurrency().pluralLowercase + "!";
    }

    @Override
    protected String getAttributeName() {
        return getCurrency().pluralLowercase;
    }
}
