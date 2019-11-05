package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;

/**
 * This is a visual ability only, logic is implemented in {@link com.mazebert.simulation.units.items.Excalibur}
 */
public strictfp class KingArthurExcalibur extends Ability<Tower> {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "True King";
    }

    @Override
    public String getDescription() {
        return "When King Arthur wields " + format.card(ItemType.Excalibur) + ", the swords stats are doubled.";
    }

    @Override
    public String getIconFile() {
        return ItemType.Excalibur.instance().getIcon();
    }
}
