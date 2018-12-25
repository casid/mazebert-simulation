package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class MoneyBinAura extends AuraAbility<Tower, Tower> {

    public MoneyBinAura() {
        super(Tower.class, 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new MoneyBinAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        MoneyBinAuraEffect ability = unit.getAbility(MoneyBinAuraEffect.class, getUnit());
        if (ability != null) {
            unit.removeAbility(ability);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Treasure Hunter";
    }

    @Override
    public String getDescription() {
        return "The " + getCurrency().singularLowercase + " bonus of towers within " + (int)getRange() + " range is increased by 60%.";
    }

    @Override
    public String getIconFile() {
        return "inn_sign_512";
    }

    @Override
    public String getLevelBonus() {
        return "+1% " + getCurrency().singularLowercase + " bonus per level.";
    }
}
