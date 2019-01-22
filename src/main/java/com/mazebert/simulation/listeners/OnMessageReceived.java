package com.mazebert.simulation.listeners;

public strictfp class OnMessageReceived extends ExposedSignal<OnMessageReceivedListener> {

    public void dispatch(int playerId, String message) {
        dispatchAll(l -> l.onMessageReceived(playerId, message));
    }
}
