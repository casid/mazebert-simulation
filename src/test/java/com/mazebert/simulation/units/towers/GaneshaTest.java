package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GaneshaTest extends SimTest {
    Ganesha ganesha;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();

        ganesha = new Ganesha();
        unitGateway.addUnit(ganesha);
    }

    @Test
    void grantsMoreExperienceToOtherTower() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        ganesha.simulate(0.1f);

        assertThat(tower.getExperienceModifier()).isEqualTo(1.2f);
    }

    @Test
    void grantsMoreExperienceToOtherTower_scalesWithGaneshaLevel() {
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        ganesha.setLevel(20);
        ganesha.simulate(0.1f);

        assertThat(tower.getExperienceModifier()).isEqualTo(1.3f);
    }

    @Test
    void levelsUpWhenOtherTowerDoes_oneLevel() {
        ganesha.setLevel(10);
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        ganesha.simulate(0.1f);
        tower.setLevel(1);

        assertThat(ganesha.getLevel()).isEqualTo(11);
    }

    @Test
    void levelsUpWhenOtherTowerDoes_tenLevels() {
        ganesha.setLevel(10);
        TestTower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        ganesha.simulate(0.1f);
        tower.setLevel(10);

        assertThat(ganesha.getLevel()).isEqualTo(20);
    }

    @Test
    void doesNothingIfOtherTowerLevelsDown() {
        ganesha.setLevel(10);
        TestTower tower = new TestTower();
        tower.setX(1);
        tower.setLevel(10);
        unitGateway.addUnit(tower);

        ganesha.simulate(0.1f);
        tower.setLevel(8);

        assertThat(ganesha.getLevel()).isEqualTo(10);
    }
}