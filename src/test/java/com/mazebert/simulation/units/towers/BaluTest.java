package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaluTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Balu balu;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        balu = new Balu();
        unitGateway.addUnit(balu);
    }

    @Test
    void cuddle_none() {
        whenBaluNeedsCuddling();
        assertThat(balu.getAttackSpeedAdd()).isEqualTo(0.0f);
        assertThat(balu.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void cuddle_oneAvailable() {
        Tower tower = new TestTower();
        unitGateway.addUnit(tower);

        whenBaluNeedsCuddling();

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(-1.0f);
        assertThat(balu.getAddedRelativeBaseDamage()).isEqualTo(0.03f);
    }

    @Test
    void cuddle_oneOutOfRange() {
        Tower tower = new TestTower();
        tower.setX(4);
        unitGateway.addUnit(tower);

        whenBaluNeedsCuddling();

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void cuddle_oneAvailable_debuffExpires() {
        Tower tower = new TestTower();
        unitGateway.addUnit(tower);

        whenBaluNeedsCuddling();

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(-1.0f);
        tower.simulate(5.0f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void cuddle_twoAvailable_random() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        Tower tower1 = new TestTower();
        unitGateway.addUnit(tower1);
        Tower tower2 = new TestTower();
        unitGateway.addUnit(tower2);

        whenBaluNeedsCuddling();

        assertThat(tower1.getAttackSpeedAdd()).isEqualTo(0.0f);
        assertThat(tower2.getAttackSpeedAdd()).isEqualTo(-1.0f);
    }

    @Test
    void customBonus() {
        Tower tower = new TestTower();
        unitGateway.addUnit(tower);

        whenBaluNeedsCuddling();
        whenBaluNeedsCuddling();
        whenBaluNeedsCuddling();

        CustomTowerBonus bonus = new CustomTowerBonus();
        balu.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Cuddled:");
        assertThat(bonus.value).isEqualTo("3x");
    }

    private void whenBaluNeedsCuddling() {
        balu.simulate(20.0f);
    }
}