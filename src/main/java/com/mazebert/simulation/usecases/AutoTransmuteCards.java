package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.AutoTransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class AutoTransmuteCards extends Usecase<AutoTransmuteCardsCommand> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @SuppressWarnings("unchecked")
    @Override
    public void execute(AutoTransmuteCardsCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        @SuppressWarnings("Duplicates") Stash<?> stash = wizard.getStash(command.cardCategory);
        if (stash == null) {
            return;
        }

        if (command.remove) {
            stash.removeAutoTransmute(command.cardType);
        } else {
            stash.addAutoTransmute(command.cardType, command.amountToKeep);
        }

    }
}
