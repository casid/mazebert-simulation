package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class LootSystemTrainer extends LootSystem {
    public LootSystemTrainer() {
        super(null, Sim.context().simulationListeners);
    }

    @Override
    public void loot(Tower tower, Creep creep) {
        // Do nothing
    }
}