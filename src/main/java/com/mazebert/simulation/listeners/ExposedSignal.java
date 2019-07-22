package com.mazebert.simulation.listeners;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import org.jusecase.signals.Signal;

public strictfp class ExposedSignal<Listener> extends Signal<Listener> {
    public final Signal<Listener> client = new Signal<>();

    public final boolean isExposed() {
        return client.size() > 0;
    }

    protected final void dispatchExposed(Consumer<Listener> event) {
        Sim.context().clientPlugin.schedule(() -> {
            for (Listener listener : client) {
                event.accept(listener);
            }
        });
    }

    // If performance is no concern, use this
    protected final void dispatchAll(Consumer<Listener> event) {
        for (Listener listener : this) {
            event.accept(listener);
        }

        if (isExposed()) {
            dispatchExposed(event);
        }
    }
}
