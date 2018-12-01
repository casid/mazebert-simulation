package com.mazebert.simulation.systems;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExperienceSystemTest extends SimTest {
    Wizard wizard;
    Wave wave;

    @BeforeEach
    void setUp() {
        difficultyGateway = new DifficultyGateway();
        experienceSystem = new ExperienceSystem(difficultyGateway);

        wizard = new Wizard();
        wave = new Wave();
    }

    @Test
    void round1() {
        wave.round = 1;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(1);
    }

    @Test
    void round2() {
        wave.round = 2;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(1);
    }

    @Test
    void round10() {
        wave.round = 10;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(2);
    }

    @Test
    void round10_modifier() {
        wave.round = 10;
        wizard.experienceModifier = 10;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(25);
    }

    @Test
    void round100() {
        wave.round = 100;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(19);
    }

    @Test
    void round10000() {
        wave.round = 10000;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(74); // capped
    }

    @Test
    void round10000_normal() {
        wave.round = 10000;
        difficultyGateway.setDifficulty(Difficulty.Normal);
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(99); // capped
    }

    @Test
    void added() {
        wizard.experience = 20;
        whenExperienceIsGranted();
        assertThat(wizard.experience).isEqualTo(21);
    }

    @Test
    void level1() {
        experienceSystem.grantExperience(wizard, 60);
        assertThat(wizard.level).isEqualTo(1);
    }

    @Test
    void level2() {
        experienceSystem.grantExperience(wizard, 600);
        assertThat(wizard.level).isEqualTo(2);
    }

    @Test
    void level38() {
        experienceSystem.grantExperience(wizard, 26000);
        assertThat(wizard.level).isEqualTo(38);
    }

    @Test
    void levelMax() {
        experienceSystem.grantExperience(wizard, 100000000000L);
        assertThat(wizard.level).isEqualTo(200);
    }

    private void whenExperienceIsGranted() {
        experienceSystem.grantExperience(wizard, wave);
    }
}