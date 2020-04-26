package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class MuliBro extends AuraAbility<Muli, Huli> {
    public MuliBro() {
        super(CardCategory.Tower, Huli.class, 100);
    }

    @Override
    protected void onAuraEntered(Huli unit) {
        MuliBroEffect broEffect = new MuliBroEffect();
        broEffect.setOrigin(getUnit());
        unit.addAbility(broEffect);
    }

    @Override
    protected void onAuraLeft(Huli unit) {
        unit.removeAbility(MuliBroEffect.class);
    }

    @Override
    protected boolean isQualifiedForAura(Huli unit) {
        return getUnit().getPlayerId() == unit.getPlayerId();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Banana Bro!";
    }

    @Override
    public String getDescription() {
        return "All Hulis on the map throw bananas to Muli. Muli can't attack unless he has bananas!";
    }

    @Override
    public String getIconFile() {
        return "0028_hide_512";
    }
}
