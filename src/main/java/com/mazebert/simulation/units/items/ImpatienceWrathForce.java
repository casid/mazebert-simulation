package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class ImpatienceWrathForce extends Item {

    public ImpatienceWrathForce() {
        super(new ImpatienceWrathForceAbility(), new ImpatienceWrathSetAbility());
    }

    @Override
    public String getName() {
        return "Unrelenting Force";
    }

    @Override
    public String getDescription() {
        return "Buzz Buzz...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "magical_substance_3_512";
    }

    @Override
    public int getItemLevel() {
        return 68;
    }

    @Override
    public String getAuthor() {
        return "Moknahr";
    }
}
