package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class MonsterTeeth extends Item {

    public MonsterTeeth() {
        super(new MonsterTeethAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Crit chance increased from 15% to 20%."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "0015_bite_512";
    }

    @Override
    public String getName() {
        return "Monster Teeth";
    }

    @Override
    public String getDescription() {
        return "These teeth haven't seen a dentist their entire life.";
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
        return "0.3";
    }
}
