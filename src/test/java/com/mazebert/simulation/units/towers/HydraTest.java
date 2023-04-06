package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class HydraTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Hydra();
    }

    @Test
    void headCount_initial() {
        assertThat(getHydraHeadCount()).isEqualTo(3);
    }

    @Test
    void headCount_lost_enough_health() {
        wizard.addHealth(-0.1f);
        assertThat(getHydraHeadCount()).isEqualTo(4);
    }

    @Test
    void headCount_lost_not_enough_health() {
        wizard.addHealth(-0.03f);
        assertThat(getHydraHeadCount()).isEqualTo(3);
    }

    @Test
    void headCount_lost_enough_health_accumulated() {
        wizard.addHealth(-0.03f);
        wizard.addHealth(-0.03f);
        wizard.addHealth(-0.03f);
        wizard.addHealth(-0.03f);

        assertThat(getHydraHeadCount()).isEqualTo(4);
    }

    @Test
    void headCount_lost_massive_amount_of_health() {
        wizard.addHealth(-0.5f);
        assertThat(getHydraHeadCount()).isEqualTo(8);
    }

    @Test
    void headCount_lost_massive_amount_of_health_then_gained_health_has_no_effect() {
        wizard.addHealth(-0.5f);
        wizard.addHealth(+1.0f);

        assertThat(getHydraHeadCount()).isEqualTo(8);
    }

    int getHydraHeadCount() {
        return ((Hydra)tower).getHeadCount();
    }
}