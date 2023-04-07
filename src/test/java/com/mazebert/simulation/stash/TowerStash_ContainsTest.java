package com.mazebert.simulation.stash;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.maps.Terrain;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TowerStash_ContainsTest extends SimTest {

    TowerStash stash;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListenersTrainer();
        stash = new TowerStash();
    }

    @Test
    void none() {
        assertThat(containsWaterTower()).isFalse();
    }

    @Test
    void one_water() {
        stash.add(TowerType.Hydra);
        assertThat(containsWaterTower()).isTrue();
    }

    @Test
    void one_land() {
        stash.add(TowerType.Huli);
        assertThat(containsWaterTower()).isFalse();
    }

    private boolean containsWaterTower() {
        return stash.contains(t -> t.getTerrain() == Terrain.Water);
    }
}
