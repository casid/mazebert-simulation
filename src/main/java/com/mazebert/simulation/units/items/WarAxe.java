package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WarAxe extends Item {

    public WarAxe() {
        super(new WarAxeAbility());
    }

    @Override
    public String getName() {
        return "War Axe";
    }

    @Override
    public String getDescription() {
        return "Made for war, it thirsts for blood.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "war_axe";
    }

    @Override
    public int getItemLevel() {
        return 22;
    }
}
