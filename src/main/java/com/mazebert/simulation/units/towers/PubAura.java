package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class PubAura extends AuraAbility<Tower, Tower> {

    public PubAura() {
        super(CardCategory.Tower, Tower.class, 2);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new PubAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.removeAbility(PubAuraEffect.class, getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Beer on Tap";
    }

    @Override
    public String getDescription() {
        return "The damage of allies in 2 range is increased by 10%.";
    }

    @Override
    public String getIconFile() {
        return "carapace_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 0.5% damage per level.";
    }
}
