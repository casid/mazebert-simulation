package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Bookworm extends Hero {

    public Bookworm() {
        addAbility(new BookwormBooksAbility());
        addAbility(new BookwormExperienceAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v13, false, 2019)
        );
    }

    @Override
    public String getIcon() {
        return "dragonhead_512";
    }

    @Override
    public String getName() {
        return "Bookworm";
    }

    @Override
    public String getDescription() {
        return "I know that I don't know anything. Did you know that?\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
