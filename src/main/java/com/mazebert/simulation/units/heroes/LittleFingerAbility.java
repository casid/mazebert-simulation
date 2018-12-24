package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class LittleFingerAbility extends HeroTowerBuffAbility {
    @Override
    protected void buffTower(Tower tower) {
        tower.addItemChance(0.2f);
        tower.addItemQuality(0.2f);
    }
}
