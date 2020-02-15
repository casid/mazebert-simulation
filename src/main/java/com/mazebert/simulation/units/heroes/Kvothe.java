package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Kvothe extends Hero {

    public Kvothe() {
        addAbility(new KvotheAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Kvothe, the Arcane";
    }

    @Override
    public String getDescription() {
        return "\"You never do things the easy way,\n do you?\" - \"There's an easy way?\"\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getIcon() {
        return "hard_mode_hero_512";
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }
}
