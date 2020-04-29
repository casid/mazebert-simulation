package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ImpatienceWrathForceAbility extends Ability<Tower> implements OnAttackListener, OnUpdateListener {
    private static final float damageBonus = 0.01f;
    private static final float resetCountdown = 3.0f;
    private static final int maxStacks = 1500;
    private static final float maxDamageBonus = damageBonus * maxStacks;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final int version = Sim.context().version;

    private float totalDamageBonus;
    private float totalCountdown;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        unit.onUpdate.remove(this);

        if (version >= Sim.v16) {
            removeBonus();
            totalCountdown = 0;
        }
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        totalCountdown = resetCountdown;

        if (totalDamageBonus < maxDamageBonus) {
            totalDamageBonus += damageBonus;
            getUnit().addAddedRelativeBaseDamage(damageBonus);
        }
    }

    @Override
    public void onUpdate(float dt) {
        if (totalDamageBonus > 0.0f) {
            if (totalCountdown > 0.0f) {
                totalCountdown -= dt;
            } else {
                removeBonus();
                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(getUnit(), "Impatience lost!", 0xbaa759);
                }
            }
        }
    }

    private void removeBonus() {
        getUnit().addAddedRelativeBaseDamage(-totalDamageBonus);
        totalDamageBonus = 0.0f;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(damageBonus) + " damage for every attack the tower produces (Maximum " + format.percent(maxDamageBonus) + "%).\nThis effect stacks and resets after " + format.seconds(resetCountdown) + " without attacking.";
    }
}
