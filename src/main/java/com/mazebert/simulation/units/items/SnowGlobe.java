package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class SnowGlobe extends Item {

    private TowerType towerType;

    @Override
    public String getName() {
        return "Snow Globe";
    }

    @Override
    public String getDescription() {
        if (towerType == null) {
            return "There is room for a common tower in here.";
        }
        return "A little " + format.card(towerType) + " lives in here.";
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
    public boolean isLight() {
        if (towerType == null) {
            return false;
        }
        return towerType.instance().isLight();
    }

    @Override
    public boolean isDark() {
        if (towerType == null) {
            return false;
        }
        return towerType.instance().isDark();
    }
}
