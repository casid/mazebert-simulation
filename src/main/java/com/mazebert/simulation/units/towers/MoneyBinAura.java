package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class MoneyBinAura extends AuraAbility<Tower, Tower> {

    public MoneyBinAura() {
        super(CardCategory.Tower, Tower.class, Sim.context().version >= Sim.vDoLEndBeta5 ? 0 : 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new MoneyBinAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.removeAbility(MoneyBinAuraEffect.class, getUnit());
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
        return "The " + getCurrency().singularLowercase + " bonus of towers within range increases by 60%.";
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
