package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class TrainingHologramTest extends SimTest {
    Wizard wizard;
    TrainingHologram trainingHologram;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        damageSystem = new DamageSystemTrainer();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
        waveSpawner = new WaveSpawner();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        trainingHologram = new TrainingHologram();
        trainingHologram.setWizard(wizard);
        trainingHologram.setX(1);
        trainingHologram.setY(2);
        unitGateway.addUnit(trainingHologram);
    }

    @Test
    void spawned() {
        Wave wave = whenWaveIsFinished();

        Creep dummy = (Creep) unitGateway.getUnit(2);
        assertThat(dummy.getX()).isEqualTo(1);
        assertThat(dummy.getY()).isEqualTo(2);
        assertThat(dummy.getHealth()).isEqualTo(1);
        assertThat(dummy.getMaxHealth()).isEqualTo(1);
        assertThat(dummy.getGold()).isEqualTo(0);
        assertThat(dummy.getWizard()).isEqualTo(wizard);
        assertThat(dummy.getArmor()).isEqualTo(1);
        assertThat(dummy.getExperience()).isEqualTo(1);
        assertThat(dummy.getType()).isEqualTo(CreepType.Spider);
        assertThat(dummy.getWave()).isNotSameAs(wave); // We need to create a new dummy wave, otherwise the wave spawner will go crazy!
        assertThat(dummy.getWave().origin).isEqualTo(WaveOrigin.TrainingDummy);
    }

    @Test
    void removedOnKill() {
        Gargoyle gargoyle = new Gargoyle();
        unitGateway.addUnit(gargoyle);

        whenWaveIsFinished();

        gargoyle.simulate(gargoyle.getCooldown());

        Creep dummy = (Creep) unitGateway.getUnit(3);
        assertThat(dummy.getHealth()).isEqualTo(0);
        dummy.simulate(5.0f);
        assertThat(unitGateway.hasUnit(dummy)).isFalse();
    }

    @Test
    void initializedOnBuildOnly() {
        unitGateway.removeUnit(trainingHologram);
        new TrainingHologram();

        whenWaveIsFinished();

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0);
    }

    private Wave whenWaveIsFinished() {
        Wave wave = new Wave();
        wave.creepType = CreepType.Spider;
        simulationListeners.onWaveFinished.dispatch(wave);
        return wave;
    }
}