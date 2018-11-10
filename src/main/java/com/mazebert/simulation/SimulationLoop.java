package com.mazebert.simulation;

public strictfp class SimulationLoop {

    public static final String THREAD_NAME = "simulation-loop";

    private final Simulation simulation = Sim.context().simulation;

    private volatile boolean running;

    public void start() {
        running = true;
        Thread thread = new Thread(this::run);
        thread.setName(THREAD_NAME);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    private void run() {
        simulation.start();
        while (running) {
            simulation.process();
        }
    }
}
