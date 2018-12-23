package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public strictfp class OnResurrect extends ExposedSignal<OnResurrectListener> {

    public void dispatch(Creep target) {
        dispatchAll(l -> l.onResurrect(target));
    }
}
