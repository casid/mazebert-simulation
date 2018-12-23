package com.mazebert.simulation.units.abilities;

public abstract strictfp class FalDamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public FalDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addDamageAgainstFal(amount);
    }

    public String getTitle() {
        return "Damage against Fal";
    }

    public String getDescription() {
        return "The damage against Fal\nis increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return "+ " + format.percent(bonusPerLevel) + "% Fal damage per level.";
    }
}
