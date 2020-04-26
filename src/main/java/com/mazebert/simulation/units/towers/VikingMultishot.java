package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class VikingMultishot extends AttackAbility {

    public VikingMultishot() {
        super(2, true);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Double Throw";
    }

    @Override
    public String getDescription() {
        return "Holgar enjoys the sound of two cracking skulls, so he throws two axes at once.";
    }

    @Override
    public String getIconFile() {
        return "0026_axeattack2_512";
    }
}
