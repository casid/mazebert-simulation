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

    @Override
    protected String getAttributeName() {
        return "boss damage";
    }
}
