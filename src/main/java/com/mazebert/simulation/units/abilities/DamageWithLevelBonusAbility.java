package com.mazebert.simulation.units.abilities;

public abstract strictfp class DamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public DamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAddedRelativeBaseDamage(amount);
    }

    public String getTitle() {
        return "More damage!";
    }

    public String getDescription() {
        return "The carrier's damage is increased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return "+ " + formatPlugin.percent(bonusPerLevel) + "% damage per level.";
    }
}
