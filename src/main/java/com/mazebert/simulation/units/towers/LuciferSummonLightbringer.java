package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.GainItemOnBuildAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.Lightbringer;

public strictfp class LuciferSummonLightbringer extends GainItemOnBuildAbility {
    @Override
    protected Item createItem() {
        return new Lightbringer();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Lightbringer";
    }

    @Override
    public String getDescription() {
        return "When Lucifer is built, he summons his legendary sword Lightbringer.";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512"; // TODO
    }
}
