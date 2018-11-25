package com.mazebert.simulation.units.abilities;

public abstract strictfp class CritChanceWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
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

    public String getDescription() {
        return "The crit chance of the carrier is\nincreased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return "+ " + formatPlugin.percent(bonusPerLevel) + "% crit chance per level.";
    }
}