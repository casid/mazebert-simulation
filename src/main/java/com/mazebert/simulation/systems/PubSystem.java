package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.towers.Pub;
import com.mazebert.simulation.units.towers.PubPartyEffect;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class PubSystem implements OnUpdateListener {
    public static final float DAMAGE_BONUS_PER_PUB = 0.4f;
    public static final float PARTY_TIME = 18.0f;
    public static final float COOLDOWN_TIME = 180.0f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private float currentPartyTimeLeft;
    private float currentCooldownLeft;

    public float getReadyProgress() {
        return (COOLDOWN_TIME - currentCooldownLeft) / COOLDOWN_TIME;
    }

    public void activate() {
        int amountOfPubs = unitGateway.getAmount(Pub.class);
        float currentBonus = DAMAGE_BONUS_PER_PUB * amountOfPubs;
        unitGateway.forEach(Tower.class, tower -> tower.addAbility(new PubPartyEffect(currentBonus)));

        currentPartyTimeLeft = PARTY_TIME;
        currentCooldownLeft = COOLDOWN_TIME;
        simulationListeners.onUpdate.add(this);
    }

    @Override
    public void onUpdate(float dt) {
        if (currentPartyTimeLeft > 0) {
            currentPartyTimeLeft -= dt;
            if (currentPartyTimeLeft <= 0) {
                unitGateway.forEach(Tower.class, tower -> tower.removeAbility(PubPartyEffect.class));
                currentPartyTimeLeft = 0;
            }
        } else {
            currentCooldownLeft -= dt;
            if (currentCooldownLeft <= 0) {
                currentCooldownLeft = 0;
                simulationListeners.onUpdate.remove(this);
            }
        }
    }
}