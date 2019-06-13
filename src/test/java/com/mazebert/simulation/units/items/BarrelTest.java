package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BarrelTest extends ItemTest {
    @Test
    void replaceCausesNoCrash() {
        whenItemIsEquipped(ItemType.Barrel);
        whenTowerIsReplaced(tower, TowerType.Beaver);
    }

    @Test
    void auraIsAppliedAfterNeighbourIsReplaced() {
        Tower neighbour = whenTowerNeighbourIsBuilt(tower, TowerType.Dandelion, 1, 0);
        whenItemIsEquipped(ItemType.Barrel);

        neighbour = whenTowerIsReplaced(neighbour, TowerType.Beaver);

        assertThat(neighbour.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
        assertThat(simulationListeners.onUnitAdded.size()).isEqualTo(1);
        assertThat(simulationListeners.onUnitRemoved.size()).isEqualTo(1);
    }

    @Test
    void auraIsAppliedCarryIsReplaced() {
        Tower neighbour = whenTowerNeighbourIsBuilt(tower, TowerType.Dandelion, 1, 0);
        whenItemIsEquipped(ItemType.Barrel);

        whenTowerIsReplaced(tower, TowerType.Beaver);

        assertThat(neighbour.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
        assertThat(simulationListeners.onUnitAdded.size()).isEqualTo(1);
        assertThat(simulationListeners.onUnitRemoved.size()).isEqualTo(1);
    }
}