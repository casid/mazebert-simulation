package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class LightbladeAcademySword extends Item {

    private final LightbladeAcademySwordAbility ability;

    public LightbladeAcademySword() {
        super(new LightbladeAcademySwordAbility());
        ability = getAbility(LightbladeAcademySwordAbility.class);
    }

    @Override
    public String getName() {
        return "Plasma Blade";
    }

    @Override
    public String getDescription() {
        return "An elegant weapon if you know what you're doing.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return ability.getBladeIcon();
    }

    @Override
    public int getItemLevel() {
        return 84;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
