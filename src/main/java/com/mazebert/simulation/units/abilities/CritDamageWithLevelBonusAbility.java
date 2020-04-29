package com.mazebert.simulation.units.abilities;

public strictfp class CritDamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public CritDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addCritDamage(amount);
    }

    public String getTitle() {
        return "Increased crit damage";
    }

    @Override
    protected String getAttributeName() {
        return "crit damage";
    }
}
