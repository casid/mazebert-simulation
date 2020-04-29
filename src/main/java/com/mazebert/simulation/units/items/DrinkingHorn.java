package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DrinkingHorn extends Item {
    public DrinkingHorn() {
        super(new DrinkingHornAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Roordahuizum";
    }

    @Override
    public String getDescription() {
        return "A warrior's favorite mug.\nEquippable only by Vikings.";
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
        return "09_horn_512";
    }

    @Override
    public String getAuthor() {
        return "FuzzyEuk";
    }

    @Override
    public int getItemLevel() {
        return 110;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        return !tower.isViking();
    }
}
