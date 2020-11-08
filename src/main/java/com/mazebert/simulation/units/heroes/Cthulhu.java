package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Cthulhu extends Hero {

    public Cthulhu() {
        addAbility(new CthulhuAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getIcon() {
        return "cthulhu_512";
    }

    @Override
    public String getName() {
        return "Cthulhu";
    }

    @Override
    public String getDescription() {
        return "In his house at R'lyeh\ndead Cthulhu waits dreaming.\n" + getWizardLevelRequirementText();
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
    public Element getElement() {
        return Element.Darkness;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
