package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemAbility;

public strictfp class EldritchClam extends Item {
    public EldritchClam() {
        super(new EldritchItemAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Eldritch Clam";
    }

    @Override
    public String getDescription() {
        return "...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public String getIcon() {
        return "0032_ring_512"; // TODO
    }

    @Override
    public int getItemLevel() {
        return 7;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }
}
