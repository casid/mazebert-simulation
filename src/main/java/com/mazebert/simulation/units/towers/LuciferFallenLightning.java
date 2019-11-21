package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.ChainAbility;

public strictfp class LuciferFallenLightning extends ChainAbility {
    public LuciferFallenLightning() {
        super(ChainViewType.RedLightning, 6);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Hell's touch";
    }

    @Override
    public String getIconFile() {
        return "red_lightning";
    }

    @Override
    public String getDescription() {
        return "Damage chains to up to " + getMaxChains() + " creeps.";
    }
}
