package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUpdateListener;

public abstract strictfp class CooldownActiveAbility extends ActiveAbility implements OnUpdateListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float currentCooldownLeft;

    @Override
    public float getReadyProgress() {
        float cooldown = getCooldown();
        return (cooldown - currentCooldownLeft) / cooldown;
    }

    public abstract float getCooldown();

    protected void startCooldown() {
        currentCooldownLeft = getCooldown();
        simulationListeners.onUpdate.add(this);
    }

    @Override
    public void onUpdate(float dt) {
        currentCooldownLeft -= dt;
        if (currentCooldownLeft <= 0) {
            currentCooldownLeft = 0;
            if (!isDisposed()) {
                onAbilityReady();
            }
            simulationListeners.onUpdate.remove(this);
        }
    }
}
