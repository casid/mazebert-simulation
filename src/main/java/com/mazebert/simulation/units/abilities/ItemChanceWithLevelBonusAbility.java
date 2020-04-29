package com.mazebert.simulation.units.abilities;

public strictfp class ItemChanceWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public ItemChanceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addItemChance(amount);
    }

    public String getTitle() {
        return "Increased item chance";
    }

    @Override
    protected String getAttributeName() {
        return "item chance";
    }
}
