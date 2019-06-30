package com.mazebert.simulation.units.abilities;

public abstract strictfp class AirDamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public AirDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addDamageAgainstAir(amount);
    }

    public String getTitle() {
        return "Damage against air";
    }

    public String getDescription() {
        return "The damage against air\nis increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return format.percentWithSignAndUnit(bonusPerLevel) + " air damage per level.";
    }
}
