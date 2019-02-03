package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GaneshaLevelAura extends AuraAbility<Tower, Tower> {
    public GaneshaLevelAura() {
        super(CardCategory.Tower, Tower.class, 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new GaneshaLevelAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        GaneshaLevelAuraEffect ability = unit.getAbility(GaneshaLevelAuraEffect.class, getUnit());
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
        return "Omniscient";
    }

    @Override
    public String getDescription() {
        return "Whenever a tower within 3 fields levels up, Ganesha will do so as well.";
    }

    @Override
    public String getIconFile() {
        return "0070_starfall_512";
    }
}
