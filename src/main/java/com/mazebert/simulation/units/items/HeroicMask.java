package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class HeroicMask extends Item {

    public HeroicMask() {
        super(new HeroicMaskAbility(), new HeroicSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Modifiers increased from 5% to 10%.", "Set bonus increased from 20% to 40% attack speed."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Heroic Mask";
    }

    @Override
    public String getDescription() {
        return "All it takes to look badass.";
    }

    @Override
    public String getIcon() {
        return "hero_mask";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 17;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
