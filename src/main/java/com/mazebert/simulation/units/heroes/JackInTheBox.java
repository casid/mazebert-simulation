package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class JackInTheBox extends Hero {

    public JackInTheBox() {
        addAbility(new JackInTheBoxAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Jack in the Box";
    }

    @Override
    public String getDescription() {
        return "He's a lousy jack. That's why he hides in a box. He can summon goblins, though.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "cage_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getAuthor() {
        return "a mysterious supporter";
    }
}
