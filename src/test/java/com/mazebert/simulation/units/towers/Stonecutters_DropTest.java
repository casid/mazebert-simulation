package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Stonecutters_DropTest extends SimTest {

    private Wizard wizard;
    private Tower metropolisTower1;
    private Tower metropolisTower2;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        metropolisTower1 = new TestTower();
        metropolisTower1.setElement(Element.Metropolis);
        unitGateway.addUnit(metropolisTower1);

        metropolisTower2 = new TestTower();
        metropolisTower2.setElement(Element.Metropolis);
        unitGateway.addUnit(metropolisTower2);

        wizard.towerStash.add(TowerType.Stonecutters);
    }

    @Test
    void auraIsNotActive() {
        assertThat(metropolisTower1.getAbility(StonecuttersAuraEffect.class)).isNull();
    }

    @Test
    void crashDoesNotHappen() {
        metropolisTower1.setLevel(StonecuttersAura.MEMBER_LEVEL_REQUIREMENT + 1);
        unitGateway.removeUnit(metropolisTower1);
        metropolisTower2.setLevel(StonecuttersAura.MEMBER_LEVEL_REQUIREMENT + 1);
    }
}