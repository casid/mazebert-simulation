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

    public String getDescription() {
        return "The item quality of the carrier is\nincreased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return "+ " + format.percent(bonusPerLevel) + "% item quality per level.";
    }
}
