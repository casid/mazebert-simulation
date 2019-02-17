package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.quests.BaluQuest;

import java.util.ArrayList;
import java.util.List;

public strictfp class BaluCuddle extends CooldownAbility<Tower> {

    public static final float BASE_COOLDOWN = 20.0f;
    public static final float SLOW_DOWN = 1.0f;
    public static final float SLOW_DOWN_TIME = 5.0f;
    public static final float DAMAGE = 0.03f;
    public static final float DAMAGE_PER_LEVEL = 0.0004f;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final List<Tower> cuddleTowers = new ArrayList<>();
    private int cuddleAmount;

    @Override
    protected float getCooldown() {
        return getUnit().getCooldown(BASE_COOLDOWN);
    }

    @Override
    protected boolean onCooldownReached() {
        Tower cuddleTower = findPotentialCuddleTower();
        if (cuddleTower == null) {
            return true;
        }

        cuddleTower.addAbility(new BaluCuddleEffect());

        float damageAdd = DAMAGE + getUnit().getLevel() * DAMAGE_PER_LEVEL;
        getUnit().addAddedRelativeBaseDamage(damageAdd);

        ++cuddleAmount;
        getUnit().getWizard().addQuestProgress(BaluQuest.class);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(cuddleTower, "Cuddles Balu", 0xbaa759);
        }

        return true;
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
        return "Every " + format.cooldown(BASE_COOLDOWN) + " a tower in range is forced to cuddle Balu and slowed down by " + format.percent(SLOW_DOWN) + "% for " + format.cooldown(SLOW_DOWN_TIME) + ".\nBalu gains " + format.percent(DAMAGE) + "% more damage.";
    }

    @Override
    public String getIconFile() {
        return "0042_holyhand_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + format.percent(DAMAGE_PER_LEVEL) + "% damage per level";
    }
}
