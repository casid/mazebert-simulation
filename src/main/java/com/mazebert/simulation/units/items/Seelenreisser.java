package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Seelenreisser extends Item {

    public Seelenreisser() {
        super(new SeelenreisserAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Seelenreisser";
    }

    @Override
    public String getDescription() {
        return "This blade is consecrated to Soltar,\nGod of Darkness.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getIcon() {
        return "seelenreisser_512";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public Element getElement() {
        return Element.Darkness;
    }
}
