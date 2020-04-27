package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class HoradricMage extends Hero {

    public HoradricMage() {
        addAbility(new HoradricMageAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "The 'Horadric' Mage";
    }

    @Override
    public String getDescription() {
        return "He's not horadric. He's not a mage.\nBut he can build anything out of a pen and some string.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "0024_stash_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
