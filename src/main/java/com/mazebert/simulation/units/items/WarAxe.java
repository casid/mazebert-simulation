package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class WarAxe extends Item {

    public WarAxe() {
        super(new WarAxeAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoL, false, 2019, "Change from Medium T-Bone Steak to War Axe"),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "War Axe";
    }

    @Override
    public String getDescription() {
        return "Made for war. Thirsty for blood.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "war_axe";
    }

    @Override
    public int getItemLevel() {
        return 22;
    }
}
