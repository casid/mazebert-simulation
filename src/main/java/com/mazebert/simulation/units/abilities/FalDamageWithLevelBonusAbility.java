package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.ArmorType;

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

    @Override
    protected String getAttributeName() {
        return format.armorType(ArmorType.Fal) + " damage";
    }
}
