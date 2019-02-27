package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class AngelicElixir extends Potion {
    public AngelicElixir() {
        super(new AngelicElixirAbility());
    }

    @Override
    public String getIcon() {
        return "9011_WingsPotion_512";
    }

    @Override
    public String getName() {
        return "Angelic Elixir";
    }

    @Override
    public String getDescription() {
        return "A blessing from the Heralds.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public Rarity getDropRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }

    @Override
    public boolean isTradingAllowed() {
        return false;
    }
}
