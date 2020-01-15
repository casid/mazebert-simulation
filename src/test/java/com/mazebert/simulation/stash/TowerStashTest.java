package com.mazebert.simulation.stash;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TowerStashTest extends SimTest {
    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListenersTrainer();
    }

    @Test
    void yggdrasilCanAlwaysDrop() {
        version = Sim.v20;
        TowerStash towerStash = new TowerStash(); // Stash with no researched elements
        assertThat(towerStash.getPossibleDrops(Rarity.Unique)).containsExactly(TowerType.Yggdrasil);
    }
}