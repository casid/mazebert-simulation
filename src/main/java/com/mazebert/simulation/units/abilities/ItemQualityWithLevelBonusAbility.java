package com.mazebert.simulation.units.abilities;

public strictfp class ItemQualityWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public ItemQualityWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addItemQuality(amount);
    }

    public String getTitle() {
        return "Increased item quality";
    }

    @Override
    protected String getAttributeName() {
        return "item quality";
    }
}
