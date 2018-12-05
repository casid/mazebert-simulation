package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.PubSystem;
import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class PubParty extends ActiveAbility {
    private final PubSystem pubSystem = Sim.context().pubSystem;

    @Override
    public float getReadyProgress() {
        return pubSystem.getReadyProgress();
    }

    @Override
    public void activate() {
        pubSystem.activate();
    }
}
