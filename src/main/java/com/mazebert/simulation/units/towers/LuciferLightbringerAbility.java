package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.GainItemOnBuildAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.Lightbringer;

public strictfp class LuciferLightbringerAbility extends GainItemOnBuildAbility {
    @Override
    protected Item createItem() {
        return new Lightbringer();
    }
}
