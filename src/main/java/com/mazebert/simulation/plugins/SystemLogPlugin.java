package com.mazebert.simulation.plugins;

public strictfp class SystemLogPlugin implements LogPlugin {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
