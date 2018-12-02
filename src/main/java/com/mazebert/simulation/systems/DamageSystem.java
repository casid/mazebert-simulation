package com.mazebert.simulation.systems;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Arrays;

public strictfp class DamageSystem {
    private static final DamageInfo DAMAGE_INFO = new DamageInfo();

    private final RandomPlugin randomPlugin;
    private final SimulationListeners simulationListeners;
    private final FormatPlugin formatPlugin;

    public DamageSystem(RandomPlugin randomPlugin, SimulationListeners simulationListeners, FormatPlugin formatPlugin) {
        this.randomPlugin = randomPlugin;
        this.simulationListeners = simulationListeners;
        this.formatPlugin = formatPlugin;
    }

    public void dealDamage(Object origin, Tower tower, Creep creep) {
        if (creep.isDead()) {
            return;
        }

        if (tower.getChanceToMiss() > 0 && tower.isNegativeAbilityTriggered(tower.getChanceToMiss())) {
            tower.onMiss.dispatch(this, creep);
            return;
        }

        rollDamage(tower);
        dealDamage(origin, tower, creep, DAMAGE_INFO.damage, DAMAGE_INFO.multicrits);
    }

    // Caution: you get a shared object, so only read the info you need. do not pass it around!
    public DamageInfo rollDamage(Tower tower) {
        double baseDamage = tower.rollBaseDamage(randomPlugin);
        baseDamage += tower.getAddedAbsoluteBaseDamage();
        baseDamage = baseDamage * (1.0 + tower.getAddedRelativeBaseDamage());
        double damage = baseDamage;

        float critChance = tower.getCritChance();

        int maxMulticrit = tower.getMulticrit();
        int rolledMulticrits = 0;
        for (int i = 0; i < maxMulticrit; ++i) {
            if (randomPlugin.getFloatAbs() < critChance) {
                damage += baseDamage * tower.getCritDamage();
                critChance *= 0.8f;
                ++rolledMulticrits;
            } else {
                break;
            }
        }

        DAMAGE_INFO.damage = damage;
        DAMAGE_INFO.multicrits = rolledMulticrits;

        return DAMAGE_INFO;
    }

    public void dealDamage(Object origin, Tower tower, Creep creep, double damage, int multicrits) {
        if (creep.isDead()) {
            return;
        }

        updateBestHit(tower, damage);
        updateTotalDamage(tower, damage);

        creep.setHealth(creep.getHealth() - damage);
        tower.onDamage.dispatch(origin, creep, damage, multicrits);

        if (multicrits > 0 && simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(tower, formatPlugin.damage(damage, multicrits), 0xff0000);
        }

        if (creep.isDead()) {
            tower.onKill.dispatch(creep);
        }
    }

    private void updateBestHit(Tower tower, double damage) {
        if (damage > tower.getBestHit()) {
            tower.setBestHit(damage);

            Wizard wizard = tower.getWizard();
            if (wizard != null && damage > wizard.bestHit) {
                wizard.bestHit = damage;
                wizard.bestHitTower = TowerType.forTower(tower);
            }
        }
    }

    private void updateTotalDamage(Tower tower, double damage) {
        tower.setTotalDamage(tower.getTotalDamage() + damage);

        Wizard wizard = tower.getWizard();
        if (wizard != null) {
            wizard.totalDamage += damage;
        }
    }

    public static final class DamageInfo {
        public double damage;
        public int multicrits;
    }

    public static final class DamageHistory {
        private static final int INITIAL_CAPACITY = 10;
        private static final int LOAD_FACTOR = 2;

        private double[] damage;
        private int[] multicrits;
        private int size;

        public DamageHistory() {
            damage = new double[INITIAL_CAPACITY];
            multicrits = new int[INITIAL_CAPACITY];
        }

        public void add(DamageInfo damageInfo) {
            if (size + 1 > damage.length) {
                damage = Arrays.copyOf(damage, size * LOAD_FACTOR);
                multicrits = Arrays.copyOf(multicrits, size * LOAD_FACTOR);
            }
            damage[size] = damageInfo.damage;
            multicrits[size] = damageInfo.multicrits;
            size++;
        }

        public int size() {
            return size;
        }

        public DamageInfo get(int index) {
            DAMAGE_INFO.damage = damage[index];
            DAMAGE_INFO.multicrits = multicrits[index];
            return DAMAGE_INFO;
        }
    }
}
