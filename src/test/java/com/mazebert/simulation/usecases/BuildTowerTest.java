package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.Trainer;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerTest extends UsecaseTest<BuildTowerCommand> {
    @Trainer
    private UnitGateway unitGateway;
    @Trainer
    private SimulationListeners simulationListeners;
    @Trainer
    private RandomPluginTrainer randomPluginTrainer;

    Tower tower;

    @BeforeEach
    void setUp() {
        usecase = new BuildTower();

        request.towerType = TowerType.Hitman;
    }

    @Test
    void level() {
        whenRequestIsExecuted();
        assertThat(tower.getLevel()).isEqualTo(1);
    }

    @Test
    void baseDamage() {
        whenRequestIsExecuted();
        assertThat(tower.getMinBaseDamage()).isEqualTo(1.0f);
        assertThat(tower.getMaxBaseDamage()).isEqualTo(12.0f);
    }

    @Override
    protected void whenRequestIsExecuted() {
        super.whenRequestIsExecuted();
        tower = (Tower) unitGateway.getUnits().get(0);
    }
}