package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class HuliTest extends SimTest {

    private Huli huli;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        huli = new Huli();
        unitGateway.addUnit(huli);
    }

    @Test
    void eat() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.02f);
    }

    @Test
    void eat_more() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.06f);
    }

    @Test
    void eat_notMoreThanMax() {
        huli.simulate(1.5f);
        for (int i = 0; i < 60; ++i) {
            huli.simulate(1.5f); // banana could be eaten here
        }

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.9999996f);
    }

    @Test
    void eat_notMoreThanMax_increasedByLevel() {
        huli.setLevel(1);
        huli.simulate(1.5f);
        for (int i = 0; i < 60; ++i) {
            huli.simulate(1.5f); // banana could be eaten here
        }

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(1.0199996f);
    }

    @Test
    void eat_notWhenAttacking() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        huli.simulate(1.5f);
        huli.simulate(1.5f); // no banana eaten, because huli is busy
        huli.simulate(1.5f); // no banana eaten, because huli is busy

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0);
    }

    @Test
    void eat_bonusText() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here

        CustomTowerBonus bonus = new CustomTowerBonus();
        huli.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Bananas:");
        assertThat(bonus.value).isEqualTo("3");
    }
}