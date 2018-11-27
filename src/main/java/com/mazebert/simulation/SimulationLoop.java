package com.mazebert.simulation;

import com.mazebert.java8.Consumer;

public strictfp class SimulationLoop {

    private final String threadName;
    private final Context context;

    private Consumer<Simulation> beforeStartConsumer;

    private volatile boolean running;
    private Thread thread;

    public SimulationLoop(String threadName, Context context) {
        this.threadName = threadName;
        this.context = context;
    }

    public void start() {
        start(null);
    }

    public void start(Consumer<Simulation> beforeStartConsumer) {
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
            // Okay then
        }
    }

    private void run() {
        Sim.setContext(context);

        Simulation simulation = new Simulation();
        if (beforeStartConsumer != null) {
            beforeStartConsumer.accept(simulation);
        }
        simulation.start();
        while (running) {
            simulation.process();
        }

        simulation.stop();
    }
}
