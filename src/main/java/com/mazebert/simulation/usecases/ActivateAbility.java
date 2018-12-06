package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ActivateAbility extends Usecase<ActivateAbilityCommand> {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(ActivateAbilityCommand command) {
        Tower tower = unitGateway.findUnit(Tower.class, command.playerId, command.towerX, command.towerY);
        if (tower == null) {
            return;
        }

        ActiveAbility ability = tower.getAbility(command.abilityType.abilityClass);
        if (ability == null) {
            return;
        }

        if (ability.isReady()) {
            ability.activate();
        }
    }
}
