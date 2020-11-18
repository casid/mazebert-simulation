package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemLegendaryAbility;

public strictfp class Necronomicon extends Item {
    public Necronomicon() {
        super(new NecronomiconAbility(), new NecronomiconSummonAbility(), new EldritchItemLegendaryAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Necronomicon";
    }

    @Override
    public String getDescription() {
        return "That is not dead which can eternal lie, and with strange aeons even death may die.";
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
        return "necronomicon";
    }

    @Override
    public int getItemLevel() {
        return 60;
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
