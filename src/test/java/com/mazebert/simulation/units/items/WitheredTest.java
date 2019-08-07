package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class WitheredTest extends ItemTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        tower.addRange(10);
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

    @Test
    void threeItems() {
        whenItemIsEquipped(ItemType.WitheredCactus, 3);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(ItemType.WitheredBandages, 2);
        whenTowerAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(1.03f);
    }

    @Test
    void threeItems_stacks() {
        whenItemIsEquipped(ItemType.WitheredCactus, 3);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(ItemType.WitheredBandages, 2);
        whenTowerAttacks();
        whenTowerAttacks();
        whenTowerAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(1.0899999f);
    }

    @Test
    void threeItems_expire() {
        whenItemIsEquipped(ItemType.WitheredCactus, 3);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(ItemType.WitheredBandages, 2);
        whenTowerAttacks();
        whenTowerAttacks();
        whenTowerAttacks();
        creep.simulate(6.0f);

        assertThat(creep.getDamageModifier()).isEqualTo(0.99999994f);
    }

    @Test
    void threeItems_oneDropped() {
        whenItemIsEquipped(ItemType.WitheredCactus, 3);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(ItemType.WitheredBandages, 2);
        whenItemIsEquipped(null, 3);
        whenTowerAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(1.0f);
    }

    @Test
    void kiwi() {
        wizard.gold = 10000;
        tower = whenTowerIsReplaced(tower, TowerType.Kiwi);
        whenItemIsEquipped(ItemType.WitheredCactus, 3);
        whenItemIsEquipped(ItemType.WitheredToadstool, 1);
        whenItemIsEquipped(ItemType.WitheredBandages, 2);
        whenTowerAttacks();

        assertThat(creep.getX()).isEqualTo(0);
    }
}