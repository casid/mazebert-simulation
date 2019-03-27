package com.mazebert.simulation.plugins;

@SuppressWarnings("unused") // Used by client
public strictfp class ReplaySleepPlugin extends SleepPlugin {
    private boolean enabled = true;

    @Override
    public void sleep(long nanos) {
        if (isEnabled()) {
            super.sleep(nanos);
        }
    }

    @Override
    public void sleepUntil(long start, long nanos) {
        if (enabled) {
            super.sleepUntil(start, nanos);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
