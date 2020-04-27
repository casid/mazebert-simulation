package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Lycaon extends Hero {

    public Lycaon() {
        addAbility(new LycaonAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Prince Lycaon";
    }

    @Override
    public String getDescription() {
        return "A man who's lost everything\nis a man to be feared.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getIcon() {
        return "0078_shapeshift_wolf_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
