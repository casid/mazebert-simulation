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

    public String getDescription() {
        return "The crit damage of the carrier is\nincreased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return "+ " + formatPlugin.percent(bonusPerLevel) + "% crit damage per level.";
    }
}
