package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class PoisonArrow extends BlackMarketItem {

    public PoisonArrow() {
        super(new PoisonArrowAbility());
    }

    @Override
    public String getName() {
        return "Hydra Arrow";
    }

    @Override
    public String getDescription() {
        return "Hydra's poisonous blood is dripping from this arrow.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.4";
    }

    @Override
    public String getIcon() {
        return "poison_arrow";
    }

    @Override
    public int getItemLevel() {
        return 76;
    }

    @Override
    public String getAuthor() {
        return "TheMarine";
    }
}
