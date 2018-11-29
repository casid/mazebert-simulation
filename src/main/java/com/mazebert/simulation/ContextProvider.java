package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.NoSleepPlugin;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystem;

public strictfp class ContextProvider {
    public static Context createContext() {
        return createContext(true);
    }

    public static Context createContext(boolean realGame) {
        Context context = new Context();
        if (realGame) {
            context.sleepPlugin = new SleepPlugin();
            context.simulationMonitor = new SimulationMonitor(true);
        } else {
            context.sleepPlugin = new NoSleepPlugin();
            context.simulationMonitor = new SimulationMonitor(false);
        }

        context.randomPlugin = new UuidRandomPlugin();
        context.formatPlugin = new FormatPlugin();

        context.difficultyGateway = new DifficultyGateway();
        context.gameGateway = new GameGateway();
        context.unitGateway = new UnitGateway();
        context.waveGateway = new WaveGateway();
        context.localCommandGateway = new LocalCommandGateway();
        context.projectileGateway = new ProjectileGateway();

        context.damageSystem = new DamageSystem(context.randomPlugin);
        context.commandExecutor = new CommandExecutor();

        context.simulationListeners = new SimulationListeners();

        return context;
    }
}