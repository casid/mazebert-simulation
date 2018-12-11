package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class SatelliteTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    Satellite satellite;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        formatPlugin = new FormatPlugin();
        damageSystem = new DamageSystemTrainer();

        wizard = new Wizard();
        wizard.gold = 100;
        unitGateway.addUnit(wizard);

        satellite = new Satellite();
        satellite.setWizard(wizard);
        unitGateway.addUnit(satellite);
    }

    @Test
    void damageDependsOnWizardGold_initial() {
        assertThat(satellite.getMinBaseDamage()).isEqualTo(8);
        assertThat(satellite.getMaxBaseDamage()).isEqualTo(12);
    }

    @Test
    void damageDependsOnWizardGold_changed() {
        wizard.addGold(1000);

        assertThat(satellite.getMinBaseDamage()).isEqualTo(27);
        assertThat(satellite.getMaxBaseDamage()).isEqualTo(40);
    }

    @Test
    void attacksCostGold() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenSatelliteAttacks();

        assertThat(wizard.gold).isEqualTo(94L);
    }

    @Test
    void attacksCostGold2() {
        wizard.addGold(1000000);
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenSatelliteAttacks();

        assertThat(wizard.gold).isEqualTo(999540L);
    }

    @Test
    void attacksCostGold_lessOnHigherLevel() {
        satellite.setLevel(99);
        wizard.addGold(1000000);
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenSatelliteAttacks();

        assertThat(wizard.gold).isEqualTo(999698L);
    }

    private void whenSatelliteAttacks() {
        satellite.simulate(satellite.getBaseCooldown());
    }
}