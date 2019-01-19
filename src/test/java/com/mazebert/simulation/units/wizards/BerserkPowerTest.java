package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BerserkPowerTest extends SimTest {
    Wizard wizard;
    private BerserkPower power;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        power = new BerserkPower();
        power.setSkillLevel(1);
        wizard.addAbility(power);
        unitGateway.addUnit(wizard);
    }

    @Test
    void bonus_level1() {
        Tower tower = new TestTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        assertThat(tower.getCritChance()).isEqualTo(0.057f);
    }

    @Test
    void bonus_level10() {
        Tower tower = new TestTower();
        tower.setWizard(wizard);
        power.setSkillLevel(10);
        unitGateway.addUnit(tower);

        assertThat(tower.getCritChance()).isEqualTo(0.120000005f);
    }

    @Test
    void bonus_towerOfOtherWizard() {
        Wizard wizard2 = new Wizard();
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);

        Tower tower = new TestTower();
        tower.setWizard(wizard2);
        unitGateway.addUnit(tower);

        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }
}