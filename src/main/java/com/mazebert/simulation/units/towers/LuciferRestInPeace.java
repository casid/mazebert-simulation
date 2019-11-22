package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.RestInPeaceAbility;

public strictfp class LuciferRestInPeace extends RestInPeaceAbility {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Eternal peace";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps rest in peace.";
    }

    @Override
    public String getIconFile() {
        return "lucifer_rip_512";
    }
}