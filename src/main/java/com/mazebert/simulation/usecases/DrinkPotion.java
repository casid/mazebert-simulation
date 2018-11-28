package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class DrinkPotion extends Usecase<DrinkPotionCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(DrinkPotionCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        Tower tower = unitGateway.findUnit(Tower.class, command.playerId, command.towerX, command.towerY);
        if (tower == null) {
            return;
        }

        if (wizard.potionStash.remove(command.potionType)) {
            Potion potion = command.potionType.instance();
            for (Ability ability : potion.getAbilities()) {
                tower.addAbility(ability);
            }
            tower.onPotionConsumed.dispatch(tower, potion);
        }
    }
}
