package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class NorlsFuryAmulet extends Item {

    public NorlsFuryAmulet() {
        super(new NorlsFuryAmuletAbility(), new NorlsFurySetAbility());
    }

    @Override
    public String getName() {
        return "Norls Guardian";
    }

    @Override
    public String getDescription() {
        return "This amulet once belonged to Norl, an ancient hero of the North. It was a gift from his true love to protect him.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0017_tribalnecklace_512";
    }

    @Override
    public int getItemLevel() {
        return 14;
    }
}
