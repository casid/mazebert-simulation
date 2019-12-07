package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Beheader extends Item {

    public Beheader() {
        super(new BeheaderCritAbility(), new BeheaderDamageAbility());
    }

    @Override
    public String getName() {
        return "Beheader";
    }

    @Override
    public String getDescription() {
        return "The philosophy of this axe is that head and body should be separate.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "beheader";
    }

    @Override
    public int getItemLevel() {
        return 58;
    }
}
