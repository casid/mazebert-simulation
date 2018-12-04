package com.mazebert.simulation.listeners;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.creeps.Creep;

public interface OnChainListener {
    void onChain(ChainViewType viewType, Creep target, Creep[] chained, int chainedLength);
}
