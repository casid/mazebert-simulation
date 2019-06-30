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

    public String getDescription() {
        return "The item chance of the carrier is\nincreased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return format.percentWithSignAndUnit(bonusPerLevel) + " item chance per level.";
    }
}
