package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class KvotheTest extends SimTest {
    Wizard wizard;
    Kvothe kvothe;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        difficultyGateway = new DifficultyGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        kvothe = new Kvothe();
        kvothe.setWizard(wizard);
    }

    @Test
    void hard() {
        difficultyGateway.setDifficulty(Difficulty.Hell);
        unitGateway.addUnit(kvothe);
        assertThat(wizard.experienceModifier).isEqualTo(2.0);
    }

    @Test
    void notHard() {
        difficultyGateway.setDifficulty(Difficulty.Nightmare);
        unitGateway.addUnit(kvothe);
        assertThat(wizard.experienceModifier).isEqualTo(1.0);
    }
}