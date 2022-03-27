package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class PaintingOfSolea extends Item {

    public PaintingOfSolea() {
        super(Sim.context().version >= Sim.vRnREnd ? new PaintingOfSoleaAbility() : new PaintingOfSoleaLegacyAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnREnd, false, 2022, "Attack speed and chance to miss stack correctly with multiple paintings equipped.", "Improved performance when many paintings are carried by Mr Iron."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Painting of Solea";
    }

    @Override
    public String getDescription() {
        return "This painting shows an unnaturally pretty girl.";
    }

    @Override
    public String getIcon() {
        return "pretty_girl";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 21;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
