package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Excalibur extends Item {

    public Excalibur() {
        super(new ExcaliburAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Excalibur";
    }

    @Override
    public String getDescription() {
        return "King Arthur's legendary sword. It slices through iron as through wood.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0095_One_Handed_Sworld_512";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }
}
