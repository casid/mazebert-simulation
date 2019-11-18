package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

/**
 * This is just a cosmetic effect to have the description on the item.
 * The actual logic is done by {@link com.mazebert.simulation.units.towers.LuciferRemoveLightbringer}
 */
public strictfp class LightbringerRemoveAbility extends Ability<Tower> {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Looming Darkness";
    }

    @Override
    public String getDescription() {
        return "When Lucifer gives Lightbringer away he turns into a fallen angel and the sword breaks in two.";
    }
}
