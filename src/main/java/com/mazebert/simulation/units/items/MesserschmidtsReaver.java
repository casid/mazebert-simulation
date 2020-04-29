package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class MesserschmidtsReaver extends Item {

    public MesserschmidtsReaver() {
        super(new MesserschmidtsReaverAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Messerschmidt's Reaver";
    }

    @Override
    public String getDescription() {
        return "The greatest battle axe ever forged. It's quite powerful ... but quite heavy.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public String getIcon() {
        return "0026_axeattack2_512";
    }

    @Override
    public int getItemLevel() {
        return 66;
    }

    @Override
    public String getAuthor() {
        return "hokkei";
    }
}
