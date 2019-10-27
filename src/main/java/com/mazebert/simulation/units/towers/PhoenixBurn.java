package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PhoenixBurn extends AuraAbility<Phoenix, Creep> {

    public PhoenixBurn() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        unit.addAbility(new PhoenixBurnEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbility(PhoenixBurnEffect.class, getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ring of Fire";
    }

    @Override
    public String getDescription() {
        return "While Phoenix is alive, all creeps in range are burned.";
    }

    @Override
    public String getIconFile() {
        return "phoenix_burn";
    }
}
