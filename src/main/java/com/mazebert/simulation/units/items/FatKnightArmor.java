package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class FatKnightArmor extends Item {

    public FatKnightArmor() {
        super(new FatKnightArmorAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Armor of a Fat Knight";
    }

    @Override
    public String getDescription() {
        return "This armor is so big, it fits 9 towers.";
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
        return "0009_cuirass_512";
    }

    @Override
    public int getItemLevel() {
        return 120;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Ghul";
    }
}
