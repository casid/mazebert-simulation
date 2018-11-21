package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class MeatMallet extends Item {

    public MeatMallet() {
        super(new MeatMalletAbility());
    }

    @Override
    public String getIcon() {
        return "0094_One_Handed_Hammer_512";
    }

    @Override
    public String getName() {
        return "Meat Mallet";
    }

    @Override
    public String getDescription() {
        return "A great tool for a good beating. Cracks flesh and bones without a problem.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 55;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }
}
