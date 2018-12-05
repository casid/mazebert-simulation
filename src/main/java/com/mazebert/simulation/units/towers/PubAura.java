package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class PubAura extends AuraAbility<Tower, Tower> {

    public PubAura() {
        super(Tower.class, 2);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new HerbWitchAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        HerbWitchAuraEffect ability = unit.getAbility(HerbWitchAuraEffect.class, getUnit());
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
        return "Beer on Tap";
    }

    @Override
    public String getDescription() {
        return "The damage of allies within 2 fields is increased by 10%.";
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
