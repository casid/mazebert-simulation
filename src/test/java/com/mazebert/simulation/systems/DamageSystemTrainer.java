package com.mazebert.simulation.systems;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class DamageSystemTrainer extends DamageSystem {
    private boolean useRealDamageSystem;
    private double constantDamage = 10; // constant damage for better testability of other stuff
    private int constantCrit = 0;

    public void givenConstantDamage(double constantDamage) {
        this.constantDamage = constantDamage;
    }

    public void givenConstantCrit(int constantCrit) {
        this.constantCrit = constantCrit;
    }

    public void givenRealDamageSystemIsUsed() {
        useRealDamageSystem = true;
    }

    @Override
    public double dealDamage(Object origin, Tower tower, Creep creep) {
        if (useRealDamageSystem) {
            return super.dealDamage(origin, tower, creep);
        }

        dealDamage(origin, tower, creep, constantDamage, constantCrit, false);
        return constantDamage;
    }

    @Override
    public DamageInfo rollDamage(Tower tower) {
        if (useRealDamageSystem) {
            return super.rollDamage(tower);
        }

        DamageInfo damageInfo = new DamageInfo();
        damageInfo.damage = constantDamage;
        damageInfo.multicrits = 0;
        return damageInfo;
    }
}