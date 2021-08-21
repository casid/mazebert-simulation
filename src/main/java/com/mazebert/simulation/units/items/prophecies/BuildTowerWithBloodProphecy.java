package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class BuildTowerWithBloodProphecy extends Prophecy {

    public BuildTowerWithBloodProphecy() {
        super(new BuildTowerWithBloodProphecyAbility());
    }

    @Override
    public String getName() {
        return "Blot to Odin";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 20;
    }


}
