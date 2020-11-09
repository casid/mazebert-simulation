package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Dagon extends Hero {
    public Dagon() {
        addAbility(new DagonAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getIcon() {
        return "dagon_512"; // TODO
    }

    @Override
    public String getName() {
        return "Dagon";
    }

    @Override
    public String getDescription() {
        return "One day the land shall sink,\nand the dark ocean floor shall ascend.\n" + getWizardLevelRequirementText();
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
        return "2.1";
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
