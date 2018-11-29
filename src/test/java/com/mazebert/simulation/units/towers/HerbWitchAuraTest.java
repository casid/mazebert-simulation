package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HerbWitchAuraTest extends SimTest {

    HerbWitch herbWitch;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();

        herbWitch = new HerbWitch();
        unitGateway.addUnit(herbWitch);
    }

    @Test
    void herbWitchOnly() {
        herbWitch.simulate(0.1f);

        assertThat(herbWitch.getAttackSpeedAdd()).isEqualTo(0.1f);
    }

    @Test
    void oneTower() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        herbWitch.simulate(0.1f);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
    }

    @Test
    void oneTower_outOfRange() {
        TestTower tower = new TestTower();
        tower.setX(2);
        unitGateway.addUnit(tower);

        herbWitch.simulate(0.1f);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void oneTower_enterOnlyOnce() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        herbWitch.simulate(0.1f);
        herbWitch.simulate(0.1f);
        herbWitch.simulate(0.1f);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
    }

    @Test
    void oneTower_removed() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);
        herbWitch.simulate(0.1f);

        unitGateway.removeUnit(tower);
        herbWitch.simulate(0.1f);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void oneTower_removed_added() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);
        herbWitch.simulate(0.1f);

        unitGateway.removeUnit(tower);
        herbWitch.simulate(0.1f);

        unitGateway.addUnit(tower);
        herbWitch.simulate(0.1f);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
    }

    @Test
    void manyTowers() {
        List<Tower> towerList = new ArrayList<>();
        for (int i = 0; i < 42; ++i) {
            TestTower tower = new TestTower();
            towerList.add(tower);
            unitGateway.addUnit(tower);
        }

        herbWitch.simulate(0.1f);

        for (Tower tower : towerList) {
            assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.1f);
        }
    }

    @Test
    void oneTower_herbWitchLevelUp() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        herbWitch.simulate(0.1f);
        herbWitch.setLevel(10);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.15f);
    }

    @Test
    void manyTowers_removeHerbWitch() {
        List<Tower> towerList = new ArrayList<>();
        for (int i = 0; i < 42; ++i) {
            TestTower tower = new TestTower();
            towerList.add(tower);
            unitGateway.addUnit(tower);
        }

        herbWitch.simulate(0.1f);
        unitGateway.removeUnit(herbWitch);
        herbWitch.dispose();

        for (Tower tower : towerList) {
            assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
        }
    }
}