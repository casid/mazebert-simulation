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

    public String getDescription() {
        return "The luck of the carrier is\nincreased by " + formatPlugin.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return "+ " + formatPlugin.percent(bonusPerLevel) + "% luck per level.";
    }
}