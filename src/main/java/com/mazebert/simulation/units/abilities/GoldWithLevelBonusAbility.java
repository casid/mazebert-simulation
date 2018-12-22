package com.mazebert.simulation.units.abilities;

public strictfp class GoldWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public GoldWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addItemChance(amount);
    }

    public String getTitle() {
        return "More " + getCurrency().pluralLowercase + "!";
    }

    public String getDescription() {
        return "The carrier of this item will find\n" + format.percent(bonus) + "% more " + getCurrency().pluralLowercase + ".";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return format.percentWithSignAndUnit(bonusPerLevel) + "% " + getCurrency().pluralLowercase + " per level.";
    }
}
