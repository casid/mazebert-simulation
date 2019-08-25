package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.AttackCreepCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class AttackCreepTest extends UsecaseTest<AttackCreepCommand> {

    Wizard wizard;
    Tower tower;
    Creep creep;

    Creep attackedCreep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.onAttackOrdered.add((w, c) -> attackedCreep = c);
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        unitGateway.addUnit(tower);

        creep = a(creep());
        unitGateway.addUnit(creep);

        usecase = new AttackCreep();

        request.playerId = 1;
        request.creepId = creep.id;
    }

    @Test
    void wizardNotFound() {
        request.playerId = 42;
        whenRequestIsExecuted();
        assertThat(attackedCreep).isNull();
    }

    @Test
    void creepNotFound() {
        request.creepId = request.creepId + 1;
        whenRequestIsExecuted();
        assertThat(attackedCreep).isNull();
    }

    @Test
    void creepDead() {
        creep.setHealth(0);
        whenRequestIsExecuted();
        assertThat(attackedCreep).isNull();
    }

    @Test
    void attack() {
        whenRequestIsExecuted();
        assertThat(attackedCreep).isSameAs(creep);
    }
}