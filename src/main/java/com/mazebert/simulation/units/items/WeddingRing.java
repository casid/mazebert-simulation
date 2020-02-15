package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp abstract class WeddingRing extends Item {

    public WeddingRing(int index) {
        super(new WeddingRingAbility(index));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Wedding Ring";
    }

    @Override
    public String getDescription() {
        return "Bound for eternity.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "wedding_ring_512";
    }

    @Override
    public int getItemLevel() {
        return 71;
    }

    @Override
    public String getAuthor() {
        return "korn7809";
    }
}
