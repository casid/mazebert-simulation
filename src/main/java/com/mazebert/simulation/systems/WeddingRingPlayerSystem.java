package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;

import static com.mazebert.simulation.systems.WeddingRingSystem.SECONDS_FOR_MARRIAGE;

public strictfp class WeddingRingPlayerSystem implements OnPotionConsumedListener, OnUpdateListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final Tower[] towers = new Tower[2];

    private float remainingSeconds = SECONDS_FOR_MARRIAGE;
    private int lastRemainginSeconds;

    public void setTower(int index, Tower tower) {
        removeListeners();

        towers[index] = tower;

        if (towers[0] != null && towers[1] != null) {
            simulationListeners.onUpdate.add(this);
            remainingSeconds = SECONDS_FOR_MARRIAGE;
            lastRemainginSeconds = SECONDS_FOR_MARRIAGE + 1;
        }
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        Tower otherTower = getOtherTower(tower);
        if (otherTower != null) {
            potion = potion.getType().create();
            potion.forEachAbility(otherTower::addAbility);
        }
    }

    private Tower getOtherTower(Tower tower) {
        for (Tower t : towers) {
            if (t != tower) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void onUpdate(float dt) {
        remainingSeconds -= dt;
        if (remainingSeconds <= 0) {
            towers[0].onPotionConsumed.add(this);
            towers[1].onPotionConsumed.add(this);

            if (simulationListeners.areNotificationsEnabled()) {
                showNotficationOnBothPartners("Just married!");
            }

            simulationListeners.onUpdate.remove(this);
        } else {
            if (simulationListeners.areNotificationsEnabled() && StrictMath.ceil(remainingSeconds) < lastRemainginSeconds) {
                lastRemainginSeconds = (int)StrictMath.ceil(remainingSeconds);
                showNotficationOnBothPartners("" + lastRemainginSeconds);
            }
        }
    }

    private void showNotficationOnBothPartners(String text) {
        simulationListeners.showNotification(towers[0], text, 0xff88aa);
        simulationListeners.showNotification(towers[1], text, 0xff88aa);
    }

    private void removeListeners() {
        simulationListeners.onUpdate.remove(this);
        for (Tower t : towers) {
            if (t != null) {
                t.onPotionConsumed.remove(this);
            }
        }
    }
}
