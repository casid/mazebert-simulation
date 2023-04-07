package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class HydraMultishotDescription extends Ability<Tower> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Multiheaded";
    }

    @Override
    public String getDescription() {
        return "Hydra has 3 heads and can make 3 attacks at a time.";
    }

    @Override
    public String getIconFile() {
        return "hydra_heads";
    }
}
