package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class GibTest extends SimTest {
    Gib gib;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();
        damageSystem = new DamageSystemTrainer();

        gib = new Gib();
        unitGateway.addUnit(gib);

        creep = a(creep());
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));
        unitGateway.addUnit(creep);
    }

    @Test
    void slow() {
        whenGibAttacks();

        assertThat(creep.getSpeedModifier()).isEqualTo(0.9f);
        creep.simulate(3.0f);
        assertThat(creep.getSpeedModifier()).isEqualTo(1.0f);
    }

    @Test
    void maxStacks() {
        whenGibAttacks();
        assertThat(creep.getSpeedModifier()).isEqualTo(0.9f);

        whenGibAttacks();
        assertThat(creep.getSpeedModifier()).isEqualTo(0.80999994f);

        whenGibAttacks();
        assertThat(creep.getSpeedModifier()).isEqualTo(0.7289999f);

        whenGibAttacks();
        assertThat(creep.getSpeedModifier()).isEqualTo(0.7289999f);
    }

    @Test
    void maxStacks_attacksResetFreeze() {
        whenGibAttacks();
        whenGibAttacks();
        whenGibAttacks();

        creep.simulate(1.0f);
        creep.simulate(1.0f);
        whenGibAttacks();
        creep.simulate(1.0f);

        assertThat(creep.getSpeedModifier()).isEqualTo(0.7289999f);
    }

    @Test
    void maxStacksIncreaseWithLevel() {
        gib.setLevel(14);

        whenGibAttacks();
        whenGibAttacks();
        whenGibAttacks();
        whenGibAttacks();

        assertThat(creep.getSpeedModifier()).isEqualTo(0.6560999f);
    }

    @Test
    void stacksExpire() {
        whenGibAttacks();
        whenGibAttacks();
        whenGibAttacks();

        creep.simulate(3.0f);
        assertThat(creep.getSpeedModifier()).isEqualTo(1.0f);
        assertThat(creep.getArmor()).isEqualTo(0);
    }

    @Test
    void armorIsIncreased() {
        whenGibAttacks();

        assertThat(creep.getArmor()).isEqualTo(1);
        creep.simulate(3.0f);
        assertThat(creep.getArmor()).isEqualTo(0);
    }

    private void whenGibAttacks() {
        gib.simulate(gib.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }
}