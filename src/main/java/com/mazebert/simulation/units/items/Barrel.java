package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Barrel extends Item {

    public Barrel() {
        super(new BarrelAbility());
    }

    @Override
    public String getName() {
        return "Irish Pub's Barrel";
    }

    @Override
    public String getDescription() {
        return "This barrel is the property of a very famous pub.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "carapace_512";
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getAuthor() {
        return "jhoijhoi";
    }
}
