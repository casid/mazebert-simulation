package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.towers.Pub;
import com.mazebert.simulation.units.towers.PubPartyEffect;

public strictfp class PubSystem implements OnUpdateListener {
    public static final float DAMAGE_BONUS_PER_PUB = 0.4f;
    public static final float PARTY_TIME = 18.0f;
    public static final float COOLDOWN_TIME = 180.0f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    private float currentCooldownLeft;
    private float currentPartyTimeLeft;

    public void activate() {
        int amountOfPubs = unitGateway.getAmount(Pub.class);
        float currentBonus = DAMAGE_BONUS_PER_PUB * amountOfPubs;
        unitGateway.forEachTower(tower -> tower.addAbility(new PubPartyEffect(currentBonus)));

        currentPartyTimeLeft = PARTY_TIME;
        currentCooldownLeft = COOLDOWN_TIME;
        simulationListeners.onUpdate.add(this);
    }

    @Override
    public void onUpdate(float dt) {
        if (version > Sim.v10) {
            currentCooldownLeft -= dt;
            if (currentPartyTimeLeft > 0) {
                currentPartyTimeLeft -= dt;
                if (currentPartyTimeLeft <= 0) {
                    unitGateway.forEachTower(tower -> tower.removeAbility(PubPartyEffect.class));
                    currentPartyTimeLeft = 0;
                }
            }

            if (currentCooldownLeft <= 0) {
                simulationListeners.onUpdate.remove(this);
            }
        } else {
            currentPartyTimeLeft -= dt;
            if (currentPartyTimeLeft <= 0) {
                unitGateway.forEachTower(tower -> tower.removeAbility(PubPartyEffect.class));
                currentPartyTimeLeft = 0;
                simulationListeners.onUpdate.remove(this);
            }
        }
    }

    public float getReadyProgress() {
        return (COOLDOWN_TIME - currentCooldownLeft) / COOLDOWN_TIME;
    }
}
