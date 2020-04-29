package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class TutorialItem extends Item {

    @Override
    public String getName() {
        return "A Note from the Developer";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getIcon() {
        return "missive_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        return true;
    }
}
