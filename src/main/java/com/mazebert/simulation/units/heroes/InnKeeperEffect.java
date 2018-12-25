package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class InnKeeperEffect extends CooldownAbility<Tower> {

    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float cooldown;

    public InnKeeperEffect() {
        rollNewCooldown();
    }

    @Override
    public float getCooldown() {
        return cooldown;
    }

    private void rollNewCooldown() {
        cooldown = randomPlugin.getFloat(10.0f, 35.0f);
    }

    @Override
    protected boolean onCooldownReached() {
        if (getUnit().hasPermanentAbility()) {
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Burp!", 0xffab00);
                simulationListeners.randomSoundNotification("tower-burp", 1.0f, "sounds/burp-01.mp3", "sounds/burp-02.mp3", "sounds/burp-03.mp3");
            }
            rollNewCooldown();
        }
        return true;
    }
}
