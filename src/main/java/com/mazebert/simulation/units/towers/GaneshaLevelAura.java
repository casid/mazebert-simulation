package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GaneshaLevelAura extends AuraAbility<Tower, Tower> {
    public GaneshaLevelAura() {
        super(CardCategory.Tower, Tower.class, Sim.context().version >= Sim.vDoLEndBeta5 ? 0 : 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new GaneshaLevelAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.removeAbility(GaneshaLevelAuraEffect.class, getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Omniscient";
    }

    @Override
    public String getDescription() {
        return "Whenever a tower in range levels up, Ganesha levels up.";
    }

    @Override
    public String getIconFile() {
        return "0070_starfall_512";
    }
}
