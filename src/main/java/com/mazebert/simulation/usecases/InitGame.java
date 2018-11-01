package com.mazebert.simulation.usecases;

import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class InitGame extends Usecase<InitGameCommand> {

    @Inject
    private RandomPlugin randomPlugin;
    @Inject
    private WaveGateway waveGateway;
    @Inject
    private GameGateway gameGateway;
    @Inject
    private UnitGateway unitGateway;

    @Override
    public void execute(InitGameCommand command) {
        randomPlugin.setSeed(command.randomSeed);
        gameGateway.getGame().id = command.gameId;

        if (unitGateway.getWizard() == null) {
            Wizard wizard = new Wizard();
            wizard.getHand().add(new Hitman());
            wizard.getHand().add(new Hitman());
            wizard.getHand().add(new Hitman());
            wizard.getHand().add(new Hitman());
            unitGateway.addUnit(wizard);
        }

        if (command.rounds > 0) {
            waveGateway.setTotalWaves(command.rounds);
            waveGateway.generateMissingWaves(randomPlugin);

            new GameCountDown().start();
            new WaveSpawner();
        }
    }

}
