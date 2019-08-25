package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.AttackCreepCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class AttackCreep extends Usecase<AttackCreepCommand> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(AttackCreepCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        Creep creep = unitGateway.findCreepById(command.creepId);
        if (creep == null) {
            return;
        }

        if (!creep.isPartOfGame()) {
            return;
        }

        wizard.onAttackOrdered.dispatch(wizard, creep);
    }
}
