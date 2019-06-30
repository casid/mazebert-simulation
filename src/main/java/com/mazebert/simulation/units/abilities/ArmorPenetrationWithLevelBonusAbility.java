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

    public String getDescription() {
        return "Attacks penetrate " + format.percent(bonus) + "% armor.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0) {
            return null;
        }

        return format.percentWithSignAndUnit(bonusPerLevel) + " armor penetration per level.";
    }
}
