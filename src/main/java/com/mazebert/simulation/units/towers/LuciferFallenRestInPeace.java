package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.RestInPeaceAbility;

public strictfp class LuciferFallenRestInPeace extends RestInPeaceAbility {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Eternal hell";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps rest in hell.";
    }

    @Override
    public String getIconFile() {
        return "lucifer_fallen_rip_512";
    }
}
