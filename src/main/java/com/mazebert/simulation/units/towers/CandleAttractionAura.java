package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class CandleAttractionAura extends AuraAbility<Candle, Creep> {
    public CandleAttractionAura() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        if (unit.getAbility(CandleAttractionEffect.class) == null) {
            unit.addAbility(new CandleAttractionEffect(getUnit()));
        }
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbility(CandleAttractionEffect.class, getUnit());
    }

    @Override
    protected boolean isQualifiedForAura(Creep unit) {
        return unit.isAir();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ooh, Shiny";
    }

    @Override
    public String getDescription() {
        return "Any flying monster that enters Candle's range moves toward Candle before continuing its path. (Monsters in range of other candles are immune).";
    }

    @Override
    public String getIconFile() {
        return "candle_512";
    }
}
