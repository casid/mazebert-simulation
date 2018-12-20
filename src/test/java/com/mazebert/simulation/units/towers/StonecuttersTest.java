package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class StonecuttersTest extends SimTest {
    Stonecutters stonecutters;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        stonecutters = new Stonecutters();
        unitGateway.addUnit(stonecutters);
    }

    @Test
    void onlyStonecutters() {
        stonecutters.setLevel(19);
        assertThat(stonecutters.getMemberCount()).isEqualTo(0);
    }

    @Test
    void onlyStonecutters_member() {
        stonecutters.setLevel(20);
        assertThat(stonecutters.getMemberCount()).isEqualTo(1);
    }

    @Test
    void someMoreTowers() {
        Tower t1 = new TestTower();
        t1.setX(10);
        t1.setY(10);
        t1.setElement(Element.Metropolis);
        t1.setLevel(20);
        unitGateway.addUnit(t1);

        Tower t2 = new TestTower();
        t2.setX(-10);
        t2.setY(-10);
        t2.setElement(Element.Metropolis);
        t2.setLevel(30);
        unitGateway.addUnit(t2);

        assertThat(stonecutters.getMemberCount()).isEqualTo(2);
        float damageBonus = 1.0f + 0.03f * (20 + 30);
        assertThat(stonecutters.getDamageAgainstFal()).isEqualTo(1.0f); // not a member
        assertThat(t1.getDamageAgainstFal()).isEqualTo(damageBonus);
        assertThat(t2.getDamageAgainstFal()).isEqualTo(damageBonus);
    }

    @Test
    void nonMetropolisTowers() {
        Tower t1 = new TestTower();
        t1.setElement(Element.Nature);
        t1.setLevel(20);
        unitGateway.addUnit(t1);

        Tower t2 = new TestTower();
        t2.setElement(Element.Darkness);
        t2.setLevel(30);
        unitGateway.addUnit(t2);

        assertThat(stonecutters.getMemberCount()).isEqualTo(0);
    }
}