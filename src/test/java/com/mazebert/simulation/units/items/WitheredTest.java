package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class WitheredTest extends ItemTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());

        creep = a(creep());
        creep.setPath(new Path(-10, 0, +10, 0));
        creep.setX(0);
        unitGateway.addUnit(creep);
    }

    @Test
    void twoItems_warpBackInTime() {
        whenItemIsEquipped(ItemType.WitheredCactus, 0);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenTowerAttacks();

        assertThat(creep.getX()).isEqualTo(-1);
    }

    @Test
    void twoItems_warpBackInTime_notTriggered() {
        randomPluginTrainer.givenFloatAbs(0.03f);

        whenItemIsEquipped(ItemType.WitheredCactus, 0);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenTowerAttacks();

        assertThat(creep.getX()).isEqualTo(0);
    }

    @Test
    void twoItems_itemDropped() {
        whenItemIsEquipped(ItemType.WitheredCactus, 0);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(null, 0);
        whenTowerAttacks();

        assertThat(creep.getX()).isEqualTo(0);
    }

    private void whenTowerAttacks() {
        tower.simulate(tower.getCooldown());
    }
}