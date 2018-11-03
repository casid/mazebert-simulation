package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.Trainer;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerTest extends UsecaseTest<BuildTowerCommand> {
    @Trainer
    private UnitGateway unitGateway;
    @Trainer
    private SimulationListeners simulationListeners;
    @Trainer
    private RandomPluginTrainer randomPluginTrainer;
    @Trainer
    private TurnGateway turnGateway = new TurnGateway(1);

    Tower tower = new TestTower();
    Tower builtTower;

    @BeforeEach
    void setUp() {
        Wizard wizard = new Wizard();
        wizard.addCardToHand(tower);
        unitGateway.addUnit(wizard);

        usecase = new BuildTower();

        request.cardId = tower.getCardId();
    }

    @Test
    void towerIsBuilt() {
        whenRequestIsExecuted();
        assertThat(builtTower).isEqualTo(tower);
    }

    @Test
    void towerNotFound() {
        request.cardId = UUID.randomUUID();

        whenRequestIsExecuted();

        assertThat(builtTower).isNull();
    }

    @Override
    protected void whenRequestIsExecuted() {
        super.whenRequestIsExecuted();
        builtTower = unitGateway.findUnit(Tower.class);
    }
}