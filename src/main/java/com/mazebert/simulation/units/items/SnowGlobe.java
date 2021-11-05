package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class SnowGlobe extends Item {

    private TowerType towerType;

    @Override
    public String getName() {
        return "Snow Globe";
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Snow Globe + Novice Wizard works for more towers."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getDescription() {
        if (towerType == null) {
            return "There is room for a common tower inside.";
        }
        return "A little " + format.card(towerType) + " lives inside.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "05_magic_globe_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }

    @Override
    public Element getElement() {
        if (towerType == null) {
            return super.getElement();
        }
        return towerType.instance().getElement();
    }

    @Override
    public boolean isTransferable() {
        return Sim.context().version < Sim.vRnR;
    }
}
