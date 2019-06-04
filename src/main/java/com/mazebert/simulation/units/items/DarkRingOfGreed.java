package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkRingOfGreed extends Item {

    public DarkRingOfGreed() {
        super(new RingOfGreedAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Dark Ring of Greed";
    }

    @Override
    public String getDescription() {
        return "A cursed ring of greed, forged in the fires of the Dark Forge.";
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
        return "0032_ring_512";
    }

    @Override
    public int getItemLevel() {
        return 7;
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
