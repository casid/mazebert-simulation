package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonDoorTest extends ItemTest {

    @BeforeEach
    void setUp() {
        difficultyGateway = new DifficultyGateway();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();
        gameGateway.getGame().map = new BloodMoor();

        tower.setX(17);
        tower.setY(13);
    }

    @Test
    void goblinSpawnsInRange() {
        whenItemIsEquipped(ItemType.DungeonDoor);
        simulationListeners.onRoundStarted.dispatch(new Wave());

        Creep goblin = unitGateway.findUnit(Creep.class, wizard.getPlayerId());
        assertThat(goblin.getX()).isEqualTo(17);
        assertThat(goblin.getY()).isEqualTo(14);
    }

    @Test
    void goblinCannotSpawn() {
        tower.setX(10000);
        simulationListeners.onRoundStarted.dispatch(new Wave());

        whenItemIsEquipped(ItemType.DungeonDoor);

        assertThat(unitGateway.hasUnits(Creep.class)).isFalse();
    }
}