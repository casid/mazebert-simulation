package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class GargoyleTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Gargoyle gargoyle;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        damageSystem = new DamageSystemTrainer();
        randomPlugin = randomPluginTrainer;

        gargoyle = new Gargoyle();
        unitGateway.addUnit(gargoyle);

        creep = a(creep());
        creep.setPath(new Path(0.0f, -10.0f, 0.0f, 10.0f));
        creep.setY(0);
        unitGateway.addUnit(creep);
    }

    @Test
    void knockback() {
        whenGargoyleAttacks();
        assertThat(creep.getY()).isEqualTo(-1);
    }

    @Test
    void knockback_level() {
        gargoyle.setLevel(16);
        whenGargoyleAttacks();
        assertThat(creep.getY()).isEqualTo(-2);
    }

    @Test
    void knockback_onlyIfTriggered() {
        randomPluginTrainer.givenFloatAbs(0.8f);
        whenGargoyleAttacks();
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void knockback_notForSteadyCreeps() {
        creep.setSteady(true);
        whenGargoyleAttacks();
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void knockback_resistance() {
        whenGargoyleAttacks();
        whenGargoyleAttacks();
        assertThat(creep.getY()).isEqualTo(-1);
    }

    @Test
    void knockback_resistance_resets() {
        whenGargoyleAttacks();
        assertThat(creep.getImmobilizeResistance()).isEqualTo(0.1f);
        creep.simulate(0.1f);
        assertThat(creep.getImmobilizeResistance()).isEqualTo(0.09f);
        creep.simulate(1.0f);
        assertThat(creep.getImmobilizeResistance()).isEqualTo(0.0f);
    }

    private void whenGargoyleAttacks() {
        gargoyle.simulate(gargoyle.getBaseCooldown());
    }
}