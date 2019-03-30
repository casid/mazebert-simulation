package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class KiwiTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    TestMap map;
    Wizard wizard;
    Kiwi kiwi;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        randomPlugin = randomPluginTrainer;

        map = new TestMap(1);
        gameGateway.getGame().map = map;

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        kiwi = new Kiwi();
        kiwi.setWizard(wizard);
        unitGateway.addUnit(kiwi);
    }

    @Test
    void haka() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        kiwi.getAbility(KiwiHaka.class).activate();
        kiwi.simulate(0.1f);
        creep.simulate(0.1f);

        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
    }
}