package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class TemplarTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Templar templar;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();

        templar = new Templar();
        templar.setLevel(1);
        unitGateway.addUnit(templar);

        creep = a(creep());
        unitGateway.addUnit(creep);
    }

    @Test
    void critEveryThirdHit() {
        creep.setHealth(100000);
        templar.setCritDamage(100000);
        randomPluginTrainer.givenFloatAbs(0.9f);

        // First attack regular crit chance
        whenTemplarAttacks();
        assertThat(creep.getHealth()).isGreaterThan(0);

        // Second attack regular crit chance
        whenTemplarAttacks();
        assertThat(creep.getHealth()).isGreaterThan(0);

        // Third attack increased crit chance
        whenTemplarAttacks();
        assertThat(creep.getHealth()).isEqualTo(0);

        // Fourth attack regular crit chance again
        creep = a(creep());
        unitGateway.addUnit(creep);
        whenTemplarAttacks();
        assertThat(creep.getHealth()).isGreaterThan(0);
    }

    @Test
    void guardAura() {
        Guard guard = new Guard();
        guard.setX(1);
        unitGateway.addUnit(guard);

        assertThat(templar.getAddedAbsoluteBaseDamage()).isEqualTo(2);
    }

    private void whenTemplarAttacks() {
        templar.simulate(templar.getCooldown());
    }
}