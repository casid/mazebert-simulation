package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkBlade extends Item {

    public DarkBlade() {
        super(new DarkBladeAbility(), new DarkItemAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Blade of Darkness";
    }

    @Override
    public String getDescription() {
        return "The Dark Forge's masterpiece.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0095_One_Handed_Sworld_512";
    }

    @Override
    public int getItemLevel() {
        return 66;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public Element getElement() {
        return Element.Darkness;
    }
}
