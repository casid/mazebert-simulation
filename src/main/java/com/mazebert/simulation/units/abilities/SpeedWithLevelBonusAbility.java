package com.mazebert.simulation.units.abilities;

public abstract strictfp class SpeedWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public SpeedWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAttackSpeed(amount);
    }

    public String getTitle() {
        return "More speed!";
    }

    public String getDescription() {
        return "The attack speed of the carrier is increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return "+ " + format.percent(bonusPerLevel) + "% attack speed per level.";
    }
}
