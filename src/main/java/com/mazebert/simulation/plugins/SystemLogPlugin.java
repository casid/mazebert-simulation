package com.mazebert.simulation.plugins;

public strictfp class SystemLogPlugin implements LogPlugin {
    @Override
    public void log(String tag, String message) {
        System.out.println(tag + ", " + message);
    }
}
