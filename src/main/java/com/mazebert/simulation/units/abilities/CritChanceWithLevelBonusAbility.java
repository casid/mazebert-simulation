package com.mazebert.simulation.units.abilities;

public strictfp class CritChanceWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public CritChanceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addCritChance(amount);
    }

    public String getTitle() {
        return "Increased crit chance";
    }

    @Override
    protected String getAttributeName() {
        return "crit chance";
    }
}
