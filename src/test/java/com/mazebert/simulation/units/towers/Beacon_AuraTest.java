package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Beacon_AuraTest extends ItemTest {

    public Beacon_AuraTest() {
        gameGateway.getGame().map = new TestMap(4);
    }

    @Override
    protected Tower createTower() {
        return new Beacon();
    }

    @Test
    void towerInRangeGetsXp() {
        Tower guard = whenTowerNeighbourIsBuilt(tower, TowerType.Guard, 1, 0);

        guard.addExperience(100);

        assertThat(guard.getExperience()).isEqualTo(75);
        assertThat(tower.getExperience()).isEqualTo(25);
    }

    @Test
    void towerInRangeGetsXp_nonLight() {
        Tower rabbit = whenTowerNeighbourIsBuilt(tower, TowerType.Rabbit, 1, 0);

        rabbit.addExperience(100);

        assertThat(rabbit.getExperience()).isEqualTo(100);
        assertThat(tower.getExperience()).isEqualTo(0);
    }

    @Test
    void towerOutOfRangeGetsXp() {
        Tower guard = whenTowerNeighbourIsBuilt(tower, TowerType.Guard, 2, 0);

        guard.addExperience(100);

        assertThat(guard.getExperience()).isEqualTo(100);
        assertThat(tower.getExperience()).isEqualTo(0);
    }

    @Test
    void towerOutOfRangeGetsXp_withHelmOfHades() {
        whenItemIsEquipped(ItemType.HelmOfHades);
        Tower guard = whenTowerNeighbourIsBuilt(tower, TowerType.Guard, 2, 0);

        guard.addExperience(100);

        assertThat(guard.getExperience()).isEqualTo(75);
        assertThat(tower.getExperience()).isEqualTo(25);
    }
}
