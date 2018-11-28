package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class SchoolBook extends Item {

    public SchoolBook() {
        super(new SchoolBookAbility());
    }

    @Override
    public String getName() {
        return "School Book";
    }

    @Override
    public String getDescription() {
        return "The cover of this book states: \"Become a successful tower in 24 days.\"";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0014_magicbook_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
