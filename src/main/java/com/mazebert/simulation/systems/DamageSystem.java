package com.mazebert.simulation.systems;

import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class DamageSystem {
    private final RandomPlugin randomPlugin;

    public DamageSystem(RandomPlugin randomPlugin) {
        this.randomPlugin = randomPlugin;
    }

    public void dealDamage(Object origin, Tower tower, Creep creep) {
        if (creep.isDead()) {
            return;
        }

        if (tower.getChanceToMiss() > 0 && tower.isNegativeAbilityTriggered(tower.getChanceToMiss())) {
            tower.onMiss.dispatch(this, creep);
            return;
        }

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

        updateBestHit(tower, damage);

        dealDamage(origin, tower, creep, damage, rolledMulticrits);
    }

    public void dealDamage(Object origin, Tower tower, Creep creep, double damage, int multicrits) {
        updateTotalDamage(tower, damage);

        creep.setHealth(creep.getHealth() - damage);
        tower.onDamage.dispatch(origin, creep, damage, multicrits);

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
}
