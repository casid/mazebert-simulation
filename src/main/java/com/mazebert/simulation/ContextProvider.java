package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.NoSleepPlugin;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.SystemLogPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;

public strictfp class ContextProvider {

    public static Context createContext(int version, boolean season, boolean realGame) {
        Context context = new Context();
        context.version = version;
        context.season = season;

        if (realGame) {
            context.sleepPlugin = new SleepPlugin();
            context.simulationMonitor = new SimulationMonitor(true);
        } else {
            context.sleepPlugin = new NoSleepPlugin();
            context.simulationMonitor = new SimulationMonitor(false);
        }

        context.randomPlugin = new UuidRandomPlugin();
        context.formatPlugin = new FormatPlugin();
        context.logPlugin = new SystemLogPlugin();

        context.simulationListeners = new SimulationListeners();

        context.difficultyGateway = new DifficultyGateway();
        context.gameGateway = new GameGateway();
        context.unitGateway = new UnitGateway();
        context.waveGateway = new WaveGateway();
        context.localCommandGateway = new GameLocalCommandGateway();
        context.projectileGateway = new ProjectileGateway();

        context.commandExecutor = new CommandExecutor();

        return context;
    }
}
