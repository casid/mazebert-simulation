package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemAbility;

public strictfp class EldritchArms extends Item {
    public EldritchArms() {
        super(new EldritchArmsAbility(), new EldritchItemAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Eldritch Arms";
    }

    @Override
    public String getDescription() {
        return "The ultimate sacrifice - adopt the grotesque appearance of Ancient Horror.";
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
        return "eldritch_arms";
    }

    @Override
    public int getItemLevel() {
        return 42;
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
