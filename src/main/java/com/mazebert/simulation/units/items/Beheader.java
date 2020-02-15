package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Beheader extends Item {

    public Beheader() {
        super(new BeheaderCritAbility(), new BeheaderDamageAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoL, false, 2019, "Change from Rare T-Bone Steak to Beheader"),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Beheader";
    }

    @Override
    public String getDescription() {
        return "The philosophy of this axe is that head and body should be separate.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "beheader";
    }

    @Override
    public int getItemLevel() {
        return 58;
    }
}
