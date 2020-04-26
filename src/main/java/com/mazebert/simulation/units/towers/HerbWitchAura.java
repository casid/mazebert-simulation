package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class HerbWitchAura extends AuraAbility<Tower, Tower> {

    public HerbWitchAura() {
        super(CardCategory.Tower, Tower.class, Sim.context().version >= Sim.vDoLEndBeta5 ? 0 : 2);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new HerbWitchAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.removeAbility(HerbWitchAuraEffect.class, getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Tasty potions";
    }

    @Override
    public String getDescription() {
        return "The attack speed of allies in range is increased by 10%.";
    }

    @Override
    public String getIconFile() {
        return "0053_charge_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 0.5% attack speed per level.";
    }
}
