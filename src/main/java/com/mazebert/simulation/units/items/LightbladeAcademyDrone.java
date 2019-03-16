package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class LightbladeAcademyDrone extends Item {

    public LightbladeAcademyDrone() {
        super(new LightbladeAcademyDroneAbility(), new LightbladeAcademySetAbility());
    }

    @Override
    public String getName() {
        return "GT1, the little robot";
    }

    @Override
    public String getDescription() {
        return "I will train you to become the greatest warrior in the galaxy.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return "gt1_512";
    }

    @Override
    public int getItemLevel() {
        return 54;
    }
}
