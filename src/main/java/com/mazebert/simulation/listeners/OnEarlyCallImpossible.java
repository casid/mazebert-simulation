package com.mazebert.simulation.listeners;

public strictfp class OnEarlyCallImpossible extends ExposedSignal<OnEarlyCallImpossibleListener> {
    public void dispatch() {
        dispatchAll(OnEarlyCallImpossibleListener::onEarlyCallImpossible);
    }
}
