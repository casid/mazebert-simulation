package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class SpectralSetTest extends ItemTest {

    @Test
    void completeSetBonus() {
        Creep creep = a(creep());

        whenItemIsEquipped(ItemType.SpectralDaggers, 0);
        whenItemIsEquipped(ItemType.SpectralCape, 1);

        for (int i = 0; i < 10; ++i) {
            tower.onAttack.dispatch(creep);
        }

        assertThat(tower.getMulticrit()).isEqualTo(8);
    }

    @Test
    void onlyDaggers() {
        Creep creep = a(creep());

        whenItemIsEquipped(ItemType.SpectralDaggers);

        for (int i = 0; i < 10; ++i) {
            tower.onAttack.dispatch(creep);
        }

        assertThat(tower.getMulticrit()).isEqualTo(5);
    }

    @Test
    void completeSetBonus_capeDropped() {
        Creep creep = a(creep());

        whenItemIsEquipped(ItemType.SpectralDaggers, 0);
        whenItemIsEquipped(ItemType.SpectralCape, 1);
        whenItemIsEquipped(null, 1);

        for (int i = 0; i < 10; ++i) {
            tower.onAttack.dispatch(creep);
        }

        assertThat(tower.getMulticrit()).isEqualTo(5);
    }

    @Test
    void completeSetBonus_daggersDropped() {
        Creep creep = a(creep());

        whenItemIsEquipped(ItemType.SpectralDaggers, 0);
        whenItemIsEquipped(ItemType.SpectralCape, 1);

        for (int i = 0; i < 10; ++i) {
            tower.onAttack.dispatch(creep);
        }

        whenItemIsEquipped(null, 0);

        assertThat(tower.getMulticrit()).isEqualTo(1);
    }

    @Test
    void otherCreepIsAttacked() {
        Creep creep1 = a(creep());
        Creep creep2 = a(creep());

        whenItemIsEquipped(ItemType.SpectralDaggers);

        tower.onAttack.dispatch(creep1);
        tower.onAttack.dispatch(creep1);
        tower.onAttack.dispatch(creep1);
        tower.onAttack.dispatch(creep2);

        assertThat(tower.getMulticrit()).isEqualTo(2);
    }
}