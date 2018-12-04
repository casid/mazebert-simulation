package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnChainListener {
    void onChain(Creep target, Creep[] chained, int chainedLength);
}
