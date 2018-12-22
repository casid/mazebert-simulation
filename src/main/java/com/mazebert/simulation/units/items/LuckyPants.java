package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class LuckyPants extends Item {

    public LuckyPants() {
        super(new LuckyPantsAbility());
    }

    @Override
    public String getName() {
        return "Lucky Pants";
    }

    @Override
    public String getDescription() {
        return "Chances to get laid are extremely high with these pants on.";
    }

    @Override
    public String getIcon() {
        return "0092_Leather_Leg_Armor_512";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 21;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }
}
