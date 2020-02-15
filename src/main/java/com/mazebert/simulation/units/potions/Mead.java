package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Mead extends Potion {

    public Mead() {
        super(new MeadAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9005_MeadPotion";
    }

    @Override
    public String getName() {
        return "Mead Bottle";
    }

    @Override
    public String getDescription() {
        return "A bottle of mead! It's said this is the drink for a true Viking!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
