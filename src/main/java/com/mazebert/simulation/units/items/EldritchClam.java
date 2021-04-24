package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemCommonAbility;

public strictfp class EldritchClam extends Item {
    public EldritchClam() {
        super(new EldritchClamAbility(), new EldritchItemCommonAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Item chance reduced from 50% to 25%."),
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Eldritch Clam";
    }

    @Override
    public String getDescription() {
        return "Taken from the deep ocean, it whispers stories from unseen horror.";
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
        return "eldritch_clam";
    }

    @Override
    public int getItemLevel() {
        return 7;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
