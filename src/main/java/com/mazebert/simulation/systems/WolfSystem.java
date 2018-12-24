package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Wolf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public strictfp class WolfSystem implements OnUnitAddedListener, OnLevelChangedListener, OnUnitRemovedListener {
    public static final float CRIT_CHANCE = 0.005f;
    public static final float CRIT_DAMAGE = 0.02f;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    private List<Wolf> wolves = new ArrayList<>();
    private Set<Tower> pretenders = new HashSet<>();
    private Wolf alphaWolf;
    private int packLevel;
    private float critChance;
    private float critDamage;


    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Wolf) {
            Wolf wolf = (Wolf) unit;
            wolves.add(wolf);
            wolf.onLevelChanged.add(this);
            update();
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Wolf) {
            Wolf wolf = (Wolf) unit;
            wolves.remove(wolf);
            wolf.onLevelChanged.remove(this);
            update();
        }
    }

    public void addPretender(Tower tower) {
        pretenders.add(tower);
        tower.onLevelChanged.add(this);
        update();
    }

    public void removePretender(Tower tower) {
        pretenders.remove(tower);
        tower.onLevelChanged.remove(this);
        update();
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        update();
    }

    private void update() {
        packLevel = 0;
        for (Wolf wolf : wolves) {
            packLevel += wolf.getLevel();
        }
        for (Tower pretender : pretenders) {
            packLevel += pretender.getLevel();
        }

        changeAlphaWolf(findAlphaWolf());
    }

    private Wolf findAlphaWolf() {
        Wolf alphaWolf = null;
        for (Wolf wolf : wolves) {
            if (alphaWolf == null || wolf.getExperience() > alphaWolf.getExperience()) {
                alphaWolf = wolf;
            }
        }
        return alphaWolf;
    }

    private void changeAlphaWolf(Wolf wolf) {
        if (alphaWolf != null) {
            removeBonus();
        }

        alphaWolf = wolf;

        if (alphaWolf != null) {
            addBonus();
        }
    }

    private void addBonus() {
        critChance = packLevel * CRIT_CHANCE;
        critDamage = packLevel * CRIT_DAMAGE;
        alphaWolf.addCritChance(critChance);
        alphaWolf.addCritDamage(critDamage);
    }

    private void removeBonus() {
        alphaWolf.addCritChance(-critChance);
        alphaWolf.addCritDamage(-critDamage);
        critChance = 0;
        critDamage = 0;
    }

    public void giveExperienceToOtherWolfTowers(Wolf wolfToIgnore, int xp) {
        for (Wolf wolf : wolves) {
            if (wolf != wolfToIgnore) {
                experienceSystem.grantExperience(wolf, xp);
            }
        }
    }

    public boolean isAlphaWolf(Wolf wolf) {
        return alphaWolf == wolf;
    }
}
