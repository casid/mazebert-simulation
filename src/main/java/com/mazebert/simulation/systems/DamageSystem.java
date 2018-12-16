package com.mazebert.simulation.systems;

import com.mazebert.simulation.*;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Arrays;

public strictfp class DamageSystem {
    private final double damageWeak = 0.7;
    private final double damageStrong = 1.3;

    private static final DamageInfo DAMAGE_INFO = new DamageInfo();

    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final FormatPlugin formatPlugin = Sim.context().formatPlugin;

    public double dealDamage(Object origin, Tower tower, Creep creep) {
        if (creep.isDead()) {
            return 0;
        }

        if (tower.getChanceToMiss() > 0 && tower.isNegativeAbilityTriggered(tower.getChanceToMiss())) {
            tower.onMiss.dispatch(this, creep);
            return 0;
        }

        rollDamage(tower);
        dealDamage(origin, tower, creep, DAMAGE_INFO.damage, DAMAGE_INFO.multicrits, true);

        return DAMAGE_INFO.damage;
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

    public void dealDamage(Object origin, Tower tower, Creep creep, double damage, int multicrits, boolean modify) {
        if (creep.isDead()) {
            return;
        }

        if (modify) {
            damage = modifyDamage(tower, creep, damage);
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

    private double modifyDamage(Tower tower, Creep creep, double damage) {
        damage *= calculateWaveDamageFactor(tower, creep.getWave());
        damage *= creep.getDamageModifier();
        if (damage < 0) {
            damage = 0;
        }
        return damage;
    }

    private double calculateWaveDamageFactor(Tower tower, Wave wave) {
        double damageFactor = calculateArmorDamageFactor(tower.getAttackType(), wave.armorType);
        if (wave.type == WaveType.Air) {
            damageFactor *= tower.getDamageAgainstAir();
        }

        switch (wave.armorType) {
            case Ber:
                damageFactor *= tower.getDamageAgainstBer();
                break;
            case Fal:
                damageFactor *= tower.getDamageAgainstFal();
                break;
            case Vex:
                damageFactor *= tower.getDamageAgainstVex();
                break;
            case Zod:
                damageFactor *= tower.getDamageAgainstZod();
                break;
        }

        return damageFactor;
    }

    private double calculateArmorDamageFactor(AttackType attackType, ArmorType armorType) {
        switch (attackType) {
            case Ber:
                switch (armorType) {
                    case Ber:
                        return 1.0;
                    case Fal:
                        return damageWeak;
                    case Vex:
                        return damageStrong;
                }
            case Fal:
                switch (armorType) {
                    case Ber:
                        return damageStrong;
                    case Fal:
                        return 1.0;
                    case Vex:
                        return damageWeak;
                }
            case Vex:
                switch (armorType) {
                    case Ber:
                        return damageWeak;
                    case Fal:
                        return damageStrong;
                    case Vex:
                        return 1.0;
                }
        }
        return 1.0;
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
