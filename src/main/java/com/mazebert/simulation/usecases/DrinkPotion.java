package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class DrinkPotion extends Usecase<DrinkPotionCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    public void execute(DrinkPotionCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        Tower tower = unitGateway.findTower(command.playerId, command.towerX, command.towerY);
        if (tower == null) {
            return;
        }

        if (gameGateway.getMap().getType() == MapType.GoldenGrounds && !wizard.ownsFoilCard(command.potionType)) {
            return;
        }

        if (command.all) {
            while (true) {
                if (drink(wizard, tower, command) == null) {
                    break;
                }
            }
        } else {
            drink(wizard, tower, command);
        }
    }

    private Potion drink(Wizard wizard, Tower tower, DrinkPotionCommand command) {
        Potion potion = wizard.potionStash.remove(command.potionType);
        if (potion != null) {
            potion.forEachAbility(tower::addAbility);
            tower.onPotionConsumed.dispatch(tower, potion);
        }
        return potion;
    }
}
