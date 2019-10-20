package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class PhoenixTest extends ItemTest {

    Phoenix phoenix;

    @Override
    protected Tower createTower() {
        phoenix = new Phoenix();
        return phoenix;
    }

    @Test
    void creepBurns() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(98.69999998062849);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(97.39999996125698);
    }

    @Test
    void creepsBurn() {
        Creep creep1 = a(creep());
        creep1.setX(+2);
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.setX(-2);
        unitGateway.addUnit(creep2);

        creep1.simulate(0.1f);
        creep2.simulate(0.1f);
        assertThat(creep1.getHealth()).isEqualTo(98.69999998062849);
        assertThat(creep2.getHealth()).isEqualTo(98.69999998062849);
        creep1.simulate(0.1f);
        creep2.simulate(0.1f);
        assertThat(creep1.getHealth()).isEqualTo(97.39999996125698);
        assertThat(creep2.getHealth()).isEqualTo(97.39999996125698);
    }

    @Test
    void creepsCanBeKilled() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(100.0f);
        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void creepLeavesAura() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(0.1f);
        creep.setX(4);
        phoenix.simulate(0.1f);
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(98.69999998062849);
    }
}