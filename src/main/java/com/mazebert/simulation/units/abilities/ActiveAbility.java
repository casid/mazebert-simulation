package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class ActiveAbility extends Ability<Tower> implements OnUpdateListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float currentCooldownLeft;

    public boolean isReady() {
        return getReadyProgress() >= 1.0f;
    }

    public float getReadyProgress() {
        float cooldown = getCooldown();
        return (cooldown - currentCooldownLeft) / cooldown;
    }

    public abstract float getCooldown();

    public abstract void activate();

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

    protected void onAbilityReady() {
        getUnit().onAbilityReady.dispatch(this);
    }
}
