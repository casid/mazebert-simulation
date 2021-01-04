package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class UnicornTears extends Potion {

    private final UnicornTearsAbility unicornTearsAbility;

    public UnicornTears() {
        super(new UnicornTearsAbility());
        unicornTearsAbility = getAbility(0, UnicornTearsAbility.class);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v23, false, 2021, "Cannot be transferred to other players anymore."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public boolean isTransferable() {
        if (Sim.context().version >= Sim.v23) {
            return false;
        }
        return super.isTransferable();
    }

    @Override
    public String getIcon() {
        return "9009_WisdomPotion";
    }

    @Override
    public String getName() {
        return "Unicorn Tears";
    }

    @Override
    public String getDescription() {
        return "Tears of the last unicorn.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }

    public void setLevels(int levels) {
        unicornTearsAbility.setLevels(levels);
    }

    @Override
    public Potion copy() {
        UnicornTears copy = (UnicornTears)super.copy();
        if (Sim.context().version >= Sim.v19) {
            copy.setLevels(unicornTearsAbility.getLevels());
        }
        return copy;
    }
}
