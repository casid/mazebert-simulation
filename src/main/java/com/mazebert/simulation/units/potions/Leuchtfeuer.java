package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Leuchtfeuer extends Potion {
    public Leuchtfeuer() {
        super(new LeuchtfeuerAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021)
        );
    }

    @Override
    public String getIcon() {
        return "leuchtfeuer_512";
    }

    @Override
    public String getName() {
        return "Leuchtfeuer";
    }

    @Override
    public String getDescription() {
        return "The fluid flame of hope.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 100;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isTradingAllowed() {
        return false;
    }

    @Override
    public boolean isTransferable() {
        return false;
    }

    @Override
    public String getAuthor() {
        return "Kami";
    }

    @Override
    public String getSinceVersion() {
        return "2.3";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
