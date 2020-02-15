package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class FrozenBook extends Item {

    public FrozenBook() {
        super(new FrozenBookAbility(), new FrozenSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Frozen Book";
    }

    @Override
    public String getDescription() {
        return "A brilliant book, but your fingers get cold as you read it.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "frozen_book_512";
    }

    @Override
    public int getItemLevel() {
        return 27;
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
