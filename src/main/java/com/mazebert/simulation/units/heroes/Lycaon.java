package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class Lycaon extends Hero {

    public Lycaon() {
        addAbility(new LycaonAbility());
    }

    @Override
    public String getName() {
        return "Prince Lycaon";
    }

    @Override
    public String getDescription() {
        return "The cursed man who lost everything,\nis the man to fear the most.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getIcon() {
        return "0078_shapeshift_wolf_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
