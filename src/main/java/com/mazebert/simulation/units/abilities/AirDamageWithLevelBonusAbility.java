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

    @Override
    protected String getAttributeName() {
        return "air damage";
    }
}
