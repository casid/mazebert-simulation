package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkCauldron extends Item {

    public DarkCauldron() {
        super(new CauldronAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Dark Witch's Cauldron";
    }

    @Override
    public String getDescription() {
        return "A cursed cauldron, forged in the fires of the Dark Forge.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "cauldron_512";
    }

    @Override
    public int getItemLevel() {
        return 50;
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
