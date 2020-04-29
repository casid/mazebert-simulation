package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class UselessMachine extends Item {

    public UselessMachine() {
        super(new UselessMachineAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "The Useless Machine";
    }

    @Override
    public String getDescription() {
        return "Nobody knows what it's supposed to do.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "useleass_machine";
    }

    @Override
    public int getItemLevel() {
        return 160;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Kyrandia";
    }
}
