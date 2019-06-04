package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkGoldCoins extends Item {

    public DarkGoldCoins() {
        super(new GoldCoinsAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Dark Gold Coins";
    }

    @Override
    public String getDescription() {
        return "Cursed gold coins, forged in the fires of the Dark Forge.";
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
        return "0061_money_512";
    }

    @Override
    public int getItemLevel() {
        return 5;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
