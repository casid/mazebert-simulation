package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonDoor_v12Test extends ItemTest {

    @BeforeEach
    void setUp() {
        version = Sim.v12;

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
        tower.simulate(DungeonDoorCooldownAbility.cooldown);

        Creep goblin = unitGateway.findUnit(Creep.class, wizard.getPlayerId());
        assertThat(goblin.getX()).isEqualTo(17);
        assertThat(goblin.getY()).isEqualTo(14);
    }

    @Test
    void goblinCannotSpawn() {
        tower.setX(10000);
        tower.simulate(DungeonDoorCooldownAbility.cooldown);

        whenItemIsEquipped(ItemType.DungeonDoor);

        assertThat(unitGateway.hasUnits(Creep.class)).isFalse();
    }

    @Test
    void cooldownCaps() {
        whenItemIsEquipped(ItemType.DungeonDoor);
        tower.addAttackSpeed(10000000000.0f);

        assertThat(tower.getItem(0).getAbility(DungeonDoorCooldownAbility.class).getCooldown()).isEqualTo(30);
    }
}