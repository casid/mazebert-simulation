package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Veleda;

public strictfp abstract class Prophecy extends Item {

    public Prophecy(Ability... abilities) {
        super(abilities);
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("Equip to fulfil this prophecy.\nFulfilled prophecies are destroyed.");
    }

    @Override
    public String getSinceVersion() {
        return "2.4";
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    public String getIcon() {
        return "prophecy";
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        return !(tower instanceof Veleda);
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isProphecy() {
        return true;
    }
}
