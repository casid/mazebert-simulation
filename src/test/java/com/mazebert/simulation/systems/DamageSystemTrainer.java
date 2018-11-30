package com.mazebert.simulation.systems;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class DamageSystemTrainer extends DamageSystem {
    public DamageSystemTrainer() {
        super(null);
    }

    @Override
    public void dealDamage(Object origin, Tower tower, Creep creep) {
        dealDamage(origin, tower, creep, tower.getMinBaseDamage(), 0); // constant damage for better testability of other stuff
    }

    @Override
    public DamageInfo rollDamage(Tower tower) {
        DamageInfo damageInfo = new DamageInfo();
        damageInfo.damage = 10; // constant damage for better testability of other stuff
        damageInfo.multicrits = 0;
        return damageInfo;
    }
}