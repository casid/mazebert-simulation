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
        return "Each time the wizard loses 10% of total health, Hydra loses a head and regrows two.";
    }

    @Override
    public String getIconFile() {
        return "0026_axeattack2_512"; // TODO
    }
}
