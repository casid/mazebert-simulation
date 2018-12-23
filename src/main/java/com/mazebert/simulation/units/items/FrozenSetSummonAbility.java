package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class FrozenSetSummonAbility extends Ability<Tower> implements OnUpdateListener, OnKillListener {
    public static final String SUMMON_NOTIFICATION = "Summoned GIB!!!";

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float remainingSeconds;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        remainingSeconds = 60;
        unit.onUpdate.add(this);
        unit.onKill.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUpdate.remove(this);
        unit.onKill.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        remainingSeconds -= dt;
        if (remainingSeconds <= 0) {
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Ready to summon Gib!", 0x0088cc);
            }
            getUnit().onUpdate.remove(this);
        }
    }

    @Override
    public void onKill(Creep target) {
        if (remainingSeconds <= 0) {
            Tower unit = getUnit();

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(unit, SUMMON_NOTIFICATION, 0x0088cc);
            }

            unit.getWizard().towerStash.add(TowerType.Gib);

            unit.removeItem(ItemType.FrozenBook);
            unit.removeItem(ItemType.FrozenCandle);
            unit.removeItem(ItemType.FrozenHeart);
            unit.removeItem(ItemType.FrozenWater);
        }
    }
}
