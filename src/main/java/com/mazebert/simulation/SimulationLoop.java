package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.commands.InitPlayerCommand;

public strictfp class SimulationLoop {

    private final String threadName;
    private final Context context;

    private InitGameCommand initGameCommand;
    private InitPlayerCommand initPlayerCommand;
    private Consumer<Simulation> beforeStartConsumer;

    private volatile boolean running;
    private Thread thread;

    public SimulationLoop(String threadName, Context context) {
        this.threadName = threadName;
        this.context = context;
    }

    public void start(InitGameCommand initGameCommand, InitPlayerCommand initPlayerCommand) {
        start(initGameCommand, initPlayerCommand, null);
    }

    public void resume(Consumer<Simulation> beforeStartConsumer) {
        start(null, null, beforeStartConsumer);
    }

    private void start(InitGameCommand initGameCommand, InitPlayerCommand initPlayerCommand, Consumer<Simulation> beforeStartConsumer) {
        this.initGameCommand = initGameCommand;
        this.initPlayerCommand = initPlayerCommand;
        this.beforeStartConsumer = beforeStartConsumer;

        running = true;
        thread = new Thread(this::run);
        thread.setName(threadName);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Perfect, we wanted to stop anyway
        }
    }

    private void run() {
        Sim.setContext(context);
        try {
            Simulation simulation = new Simulation();
            if (beforeStartConsumer != null) {
                beforeStartConsumer.accept(simulation);
                simulation.resume();
            } else {
                simulation.start(initGameCommand, initPlayerCommand);
            }
            while (running && simulation.isRunning()) {
                simulation.process();
            }

            simulation.stop();
        } catch (Throwable throwable) {
            context.simulationListeners.onError.dispatch(throwable);
        } finally {
            Sim.resetContext();
        }
    }
}
