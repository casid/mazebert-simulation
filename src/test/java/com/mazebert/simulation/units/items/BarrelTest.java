package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.Test;

strictfp class BarrelTest extends ItemTest {
    @Test
    void replaceCausesNoCrash() {
        whenItemIsEquipped(ItemType.Barrel);
        whenTowerIsReplaced(tower, TowerType.Beaver);
    }
}