package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Sacrifice extends Potion {
    public Sacrifice() {
        super(new SacrificeAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9008_SacrificePotion";
    }

    @Override
    public String getName() {
        return "Soul Flask";
    }

    @Override
    public String getDescription() {
        return "The needs of the many outweigh the needs of the few. Or the one.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 56;
    }

    @Override
    public String getAuthor() {
        return "Robert Ruehlmann";
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public boolean isDestructive() {
        return true;
    }
}
