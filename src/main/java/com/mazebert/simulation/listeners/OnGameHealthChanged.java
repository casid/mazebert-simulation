package com.mazebert.simulation.listeners;

public strictfp class OnGameHealthChanged extends ExposedSignal<OnGameHealthChangedListener> {
    public void dispatch(float oldHealth, float newHealth) {
        for (OnGameHealthChangedListener listener : this) {
            listener.onGameHealthChanged(oldHealth, newHealth);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onGameHealthChanged(oldHealth, newHealth));
        }
    }
}
