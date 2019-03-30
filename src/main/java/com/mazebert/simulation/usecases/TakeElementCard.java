package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.TakeElementCardCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.Research;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class TakeElementCard extends Usecase<TakeElementCardCommand> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(TakeElementCardCommand command) {
        if (command.card == null) {
            return;
        }

        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        if (wizard.elementResearchPoints <= 0) {
            return;
        }

        Potion potion = command.card.instance();
        if (!(potion instanceof Research)) {
            return;
        }

        if (wizard.potionStash.isUniqueAlreadyDropped(command.card)) {
            return;
        }

        wizard.potionStash.add(command.card);
        --wizard.elementResearchPoints;
    }
}
