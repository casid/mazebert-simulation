package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class PaintingOfSoleaTest extends ItemTest {
    @BeforeEach
    void setUp() {
        version = Sim.vRnREnd;
    }

    @Test
    void oneItem() {
        whenItemIsEquipped(ItemType.PaintingOfSolea);

        assertThat(tower.getLuck()).isEqualTo(1.05f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
        assertThat(tower.getChanceToMiss()).isEqualTo(0.2f);
    }

    @Test
    void twoItems() {
        whenItemIsEquipped(ItemType.PaintingOfSolea, 0);
        whenItemIsEquipped(ItemType.PaintingOfSolea, 1);

        assertThat(tower.getLuck()).isEqualTo(1.0999999f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.2f);
        assertThat(tower.getChanceToMiss()).isEqualTo(0.4f);
    }

    @Test
    void twoItems_oneRemoved() {
        whenItemIsEquipped(ItemType.PaintingOfSolea, 0);
        whenItemIsEquipped(ItemType.PaintingOfSolea, 1);
        whenItemIsUnequipped(0);

        assertThat(tower.getLuck()).isEqualTo(1.0499998f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
        assertThat(tower.getChanceToMiss()).isEqualTo(0.2f);
    }

    @Test
    void fourItems_missedAttack() {
        whenItemIsEquipped(ItemType.PaintingOfSolea, 0);
        whenItemIsEquipped(ItemType.PaintingOfSolea, 1);
        whenItemIsEquipped(ItemType.PaintingOfSolea, 2);
        whenItemIsEquipped(ItemType.PaintingOfSolea, 3);

        tower.onMiss.dispatch(tower, a(creep()));

        assertThat(tower.getExperience()).isEqualTo(4.0f);
    }
}