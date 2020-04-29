package com.mazebert.simulation.units.abilities;

public strictfp class ArmorPenetrationWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public ArmorPenetrationWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addArmorPenetration(amount);
    }

    public String getTitle() {
        return "Armor penetration";
    }

    @Override
    protected String getAttributeName() {
        return "armor penetration";
    }
}
