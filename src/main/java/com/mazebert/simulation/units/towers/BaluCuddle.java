package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.quests.BaluQuest;

import java.util.ArrayList;
import java.util.List;

public strictfp class BaluCuddle extends CooldownAbility<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnRoundStartedListener {

    public static final float BASE_COOLDOWN = 20.0f;
    public static final float SLOW_DOWN = 1.0f;
    public static final float SLOW_DOWN_TIME = 5.0f;
    public static final float DAMAGE_COOLDOWN = 0.03f;
    public static final float DAMAGE_COOLDOWN_PER_LEVEL = 0.0004f;
    public static final float DAMAGE = 0.04f;
    public static final float DAMAGE_PER_LEVEL = 0.0006f;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final int version = Sim.context().version;
    private final List<Tower> cuddleTowers = new ArrayList<>();
    private int cuddleAmount;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        if (version >= Sim.v13) {
            unit.onUnitAdded.add(this);
            unit.onUnitRemoved.add(this);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        if (version >= Sim.v13) {
            unit.onUnitAdded.remove(this);
            unit.onUnitRemoved.remove(this);
        }
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        simulationListeners.onRoundStarted.add(this);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        simulationListeners.onRoundStarted.remove(this);
    }

    @Override
    protected float getCooldown() {
        if (version >= Sim.v13) {
            return Balancing.MAX_COOLDOWN; // Cooldown has no effect anymore, this became a round based ability
        } else if (version >= Sim.v12) {
            return StrictMath.max(1.0f, getUnit().getCooldown(BASE_COOLDOWN));
        } else {
            return getUnit().getCooldown(BASE_COOLDOWN);
        }
    }

    @Override
    protected boolean onCooldownReached() {
        if (version < Sim.v13) {
            cuddle(DAMAGE_COOLDOWN, DAMAGE_COOLDOWN_PER_LEVEL);
        }
        return true;
    }

    @Override
    public void onRoundStarted(Wave wave) {
        cuddle(DAMAGE, DAMAGE_PER_LEVEL);
    }

    private void cuddle(float damage, float damagePerLevel) {
        Tower cuddleTower = findPotentialCuddleTower();
        if (cuddleTower == null) {
            return;
        }

        cuddleTower.addAbility(new BaluCuddleEffect());

        float damageAdd = damage + getUnit().getLevel() * damagePerLevel;
        getUnit().addAddedRelativeBaseDamage(damageAdd);

        ++cuddleAmount;
        getUnit().getWizard().addQuestProgress(BaluQuest.class);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(cuddleTower, "Cuddles Balu", 0xbaa759);
        }
    }

    private Tower findPotentialCuddleTower() {
        try {
            unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), getUnit().getRange(), Tower.class, this::addTempPotentialCuddleTower);
            if (cuddleTowers.isEmpty()) {
                return null;
            }

            return randomPlugin.get(cuddleTowers);
        } finally {
            cuddleTowers.clear();
        }
    }

    private void addTempPotentialCuddleTower(Tower tower) {
        if (tower != getUnit()) {
            cuddleTowers.add(tower);
        }
    }

    public int getCuddleAmount() {
        return cuddleAmount;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Cuddle";
    }

    @Override
    public String getDescription() {
        if (version < Sim.v13) {
            return "Every " + format.cooldown(BASE_COOLDOWN) + " a tower in range is forced to cuddle Balu and slowed down by " + format.percent(SLOW_DOWN) + "% for " + format.cooldown(SLOW_DOWN_TIME) + ".\nBalu gains " + format.percent(DAMAGE_COOLDOWN) + "% more damage.";
        } else {
            return "Every round a tower in range is forced to cuddle Balu and slowed down by " + format.percent(SLOW_DOWN) + "% for " + format.cooldown(SLOW_DOWN_TIME) + ".\nBalu gains " + format.percent(DAMAGE) + "% more damage.";
        }
    }

    @Override
    public String getIconFile() {
        return "0042_holyhand_512";
    }

    @Override
    public String getLevelBonus() {
        if (version < Sim.v13) {
            return "+ " + format.percent(DAMAGE_COOLDOWN_PER_LEVEL) + "% damage per level";
        } else {
            return "+ " + format.percent(DAMAGE_PER_LEVEL) + "% damage per level";
        }
    }
}
