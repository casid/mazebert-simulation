package com.mazebert.simulation.units.abilities;

public abstract strictfp class BossDamageWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public BossDamageWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addDamageAgainstBosses(amount);
    }

    public String getTitle() {
        return "Damage against bosses";
    }

    public String getDescription() {
        return "The damage against bosses\nis increased by " + format.percent(bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return format.percentWithSignAndUnit(bonusPerLevel) + " per level.";
    }
}
