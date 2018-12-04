package com.mazebert.simulation.listeners;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.creeps.Creep;

import java.util.Arrays;

public strictfp class OnChain extends ExposedSignal<OnChainListener> {
    public void dispatch(ChainViewType viewType, Creep target, Creep[] chained, int chainedLength) {
        for (OnChainListener listener : this) {
            listener.onChain(viewType, target, chained, chainedLength);
        }

        if (isExposed()) {
            Creep[] chainedCopy = Arrays.copyOf(chained, chainedLength);
            dispatchExposed(l -> l.onChain(viewType, target, chainedCopy, chainedLength));
        }
    }
}
