package com.mazebert.simulation.units.abilities;

public strictfp class LuckWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public LuckWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addLuck(amount);
    }

    public String getTitle() {
        return "Increased luck";
    }

    @Override
    protected String getAttributeName() {
        return "luck";
    }
}
