package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.heroes.Hero;
import com.mazebert.simulation.units.heroes.HeroType;

public strictfp class InitPlayer extends Usecase<InitPlayerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(InitPlayerCommand command) {
        addHero(command);
    }

    private void addHero(InitPlayerCommand command) {
        HeroType heroType = HeroType.forId(command.heroId);
        Hero hero = heroType.create();
        hero.setWizard(unitGateway.getWizard(command.playerId));
        unitGateway.addUnit(hero);
    }

}
