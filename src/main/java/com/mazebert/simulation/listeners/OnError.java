package com.mazebert.simulation.listeners;

public strictfp class OnError extends ExposedSignal<OnErrorListener> {
    public void dispatch(Throwable throwable) {
        dispatchExposed(l -> l.onError(throwable));
    }
}
