package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemUniqueAbility;

public strictfp class EldritchPearl extends Item {
    public EldritchPearl() {
        super(new EldritchPearlAbility(), new EldritchItemUniqueAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Eldritch Pearl";
    }

    @Override
    public String getDescription() {
        return "It longs to return home.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public String getIcon() {
        return "eldritch_pearl";
    }

    @Override
    public int getItemLevel() {
        return 80;
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
