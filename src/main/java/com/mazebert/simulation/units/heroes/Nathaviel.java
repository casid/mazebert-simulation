package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class Nathaviel extends Hero {

    public Nathaviel() {
        addAbility(new NathavielBooksAbility());
        addAbility(new NathavielExperienceAbility());
    }

    @Override
    public String getIcon() {
        return "dragonhead_512";
    }

    @Override
    public String getName() {
        return "Nathaviel, the Bookworm";
    }

    @Override
    public String getDescription() {
        return "I know you don't know anything.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 48;
    }

    @Override
    public String getSinceVersion() {
        return "1.7";
    }
}
