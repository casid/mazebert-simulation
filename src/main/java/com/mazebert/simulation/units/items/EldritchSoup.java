package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemLegendaryAbility;

public strictfp class EldritchSoup extends Item {

    public EldritchSoup() {
        super(new EldritchSoupAbility(), new EldritchItemLegendaryAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Cthulhu's Soup";
    }

    @Override
    public String getDescription() {
        return "Where you insane before or after drinking this? Probably before.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public String getIcon() {
        return "eldritch_soup";
    }

    @Override
    public int getItemLevel() {
        return 76;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
