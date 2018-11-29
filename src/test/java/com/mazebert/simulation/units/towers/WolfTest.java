package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.WolfSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class WolfTest extends SimTest {
    Wolf wolf;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        wolfSystem = new WolfSystem(simulationListeners);
        wolfSystem.init();

        wolf = new Wolf();
        wolf.setExperience(Balancing.getTowerExperienceForLevel(10));
        unitGateway.addUnit(wolf);
    }

    @AfterEach
    void tearDown() {
        wolfSystem.dispose();
    }

    @Test
    void oneWolfIsAlphaWolf() {
        assertThat(wolf.getCritChance()).isEqualTo(0.099999994f);
    }

    @Test
    void oneBiggerWolfBecomesAlphaWolf() {
        Wolf biggerWolf = new Wolf();
        biggerWolf.setExperience(Balancing.getTowerExperienceForLevel(100));
        unitGateway.addUnit(biggerWolf);

        assertThat(biggerWolf.getCritChance()).isEqualTo(0.595f);
        assertThat(wolf.getCritChance()).isEqualTo(0.049999997f);
    }

    @Test
    void alphaWolfIsRemoved() {
        Wolf smallerWolf = new Wolf();
        smallerWolf.setExperience(Balancing.getTowerExperienceForLevel(1));
        unitGateway.addUnit(smallerWolf);

        unitGateway.removeUnit(wolf);

        assertThat(smallerWolf.getCritChance()).isEqualTo(0.055f);
        assertThat(wolf.getCritChance()).isEqualTo(0.049999997f);
    }

    @Test
    void customTowerBonus() {
        Wolf smallerWolf = new Wolf();
        smallerWolf.setExperience(Balancing.getTowerExperienceForLevel(1));
        unitGateway.addUnit(smallerWolf);

        CustomTowerBonus bonus = new CustomTowerBonus();
        smallerWolf.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Status:");
        assertThat(bonus.value).isEqualTo("Pack wolf");

        wolf.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Status:");
        assertThat(bonus.value).isEqualTo("Alpha wolf");
    }
}