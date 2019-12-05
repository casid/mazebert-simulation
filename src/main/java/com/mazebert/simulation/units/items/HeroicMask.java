package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class HeroicMask extends Item {

    public HeroicMask() {
        super(new HeroicMaskAbility(), new HeroicSetAbility());
    }

    @Override
    public String getName() {
        return "Heroic Mask";
    }

    @Override
    public String getDescription() {
        return "Put it on. It's all it takes to become bad-ass.";
    }

    @Override
    public String getIcon() {
        return "0100_Wooden_Bow_512"; // TODO
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 17;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public boolean isLight() {
        return true;
    }
}
