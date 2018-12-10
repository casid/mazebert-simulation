package com.mazebert.simulation.listeners;

public strictfp class OnEarlyCallPossible extends ExposedSignal<OnEarlyCallPossibleListener> {
    public void dispatch() {
        dispatchAll(OnEarlyCallPossibleListener::onEarlyCallPossible);
    }
}
