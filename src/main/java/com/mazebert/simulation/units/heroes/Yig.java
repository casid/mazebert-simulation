package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Yig extends Hero {

    public Yig() {
        // TODO
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getIcon() {
        return "yig_512"; // TODO
    }

    @Override
    public String getName() {
        return "Yig";
    }

    @Override
    public String getDescription() {
        return "TODO.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }
}
