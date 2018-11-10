package com.mazebert.simulation;

public strictfp class SimulationLoop {

    private final String threadName;
    private final Context context;

    private volatile boolean running;

    public SimulationLoop(String threadName, Context context) {
        this.threadName = threadName;
        this.context = context;
    }

    public void start() {
        running = true;
        Thread thread = new Thread(this::run);
        thread.setName(threadName);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    private void run() {
        Sim.setContext(context);

        Simulation simulation = new Simulation();
        simulation.start();
        while (running) {
            simulation.process();
        }
    }
}
