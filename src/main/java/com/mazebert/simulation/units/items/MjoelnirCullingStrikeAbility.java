package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.CullingStrikeAbility;

public strictfp class MjoelnirCullingStrikeAbility extends CullingStrikeAbility {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Lightning Strike";
    }

    @Override
    public String getDescription() {
        return "If Mjoelnir leaves a damaged creep with less than " + format.percent(HEALTH_THRESHOLD) + "% health, it is killed.";
    }
}
