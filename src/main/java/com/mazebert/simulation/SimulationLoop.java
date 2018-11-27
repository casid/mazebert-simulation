package com.mazebert.simulation;

import com.mazebert.simulation.replay.ReplayReader;

public strictfp class SimulationLoop {

    private final String threadName;
    private final Context context;

    private ReplayReader replayReader;

    private volatile boolean running;
    private Thread thread;

    public SimulationLoop(String threadName, Context context) {
        this.threadName = threadName;
        this.context = context;
    }

    public void start(ReplayReader replayReader) {
        this.replayReader = replayReader;

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
        if (replayReader != null) {
            replayReader.readHeader();
            simulation.load(replayReader);
            replayReader.close();
        }
        simulation.start();
        while (running) {
            simulation.process();
        }

        simulation.stop();
    }
}
