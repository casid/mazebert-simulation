package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.heroes.Hero;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class InitPlayer extends Usecase<InitPlayerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(InitPlayerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        initWizard(wizard, command);
        addHero(wizard, command);

        wizard.foilHeroes = command.foilHeroes;
        wizard.foilTowers = command.foilTowers;
        wizard.foilPotions = command.foilPotions;
        wizard.foilItems = command.foilItems;
    }

    public void initWizard(Wizard wizard, InitPlayerCommand command) {
        if (command.ladderPlayerId > 0) {
            wizard.ladderPlayerId = command.ladderPlayerId;
            wizard.name = command.playerName;
            wizard.experience = command.experience;
        }
    }

    private void addHero(Wizard wizard, InitPlayerCommand command) {
        Hero hero = command.heroType.create();
        hero.setWizard(wizard);
        unitGateway.addUnit(hero);
    }

}
