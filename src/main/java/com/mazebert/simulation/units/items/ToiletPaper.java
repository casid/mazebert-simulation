package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ToiletPaper extends Item {

    public ToiletPaper() {
        super(new ToiletPaperMummyAbility(), new ToiletPaperTransmuteAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vCorona, false, 2020)
        );
    }

    @Override
    public Element getElement() {
        return Element.Metropolis;
    }

    @Override
    public String getName() {
        return "Toilet Paper";
    }

    @Override
    public String getDescription() {
        return "You may be dead,\nbut your arse is clean.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.1";
    }

    @Override
    public String getIcon() {
        return "0021_cloth_512";
    }

    @Override
    public int getItemLevel() {
        return 50;
    }
}
