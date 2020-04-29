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
        return "Damaged creeps are killed instantly at " + format.percent(HEALTH_THRESHOLD) + "% of maximum life or less.";
    }
}
