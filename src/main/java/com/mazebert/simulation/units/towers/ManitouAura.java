package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class ManitouAura extends AuraAbility<Tower, Tower> {
    private static final int BONUS = 2;

    public ManitouAura() {
        super(Tower.class, 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addMulticrit(+BONUS);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addMulticrit(-BONUS);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Buffalo Totem";
    }

    @Override
    public String getDescription() {
        return "Other towers feel the presence of the Great Spirit. They are blessed with the strength of the buffalo.";
    }

    @Override
    public String getIconFile() {
        return "0017_tribalnecklace_512";
    }

    @Override
    public String getLevelBonus() {
        return "+" + BONUS + " multicrit for all towers\nwithin " + (int)getRange() + " range.";
    }
}
