package com.mazebert.simulation.systems;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class DamageSystemTrainer extends DamageSystem {
    private double constantDamage = 10;  // constant damage for better testability of other stuff

    public void givenConstantDamage(double constantDamage) {
        this.constantDamage = constantDamage;
    }

    @Override
    public double dealDamage(Object origin, Tower tower, Creep creep) {
        dealDamage(origin, tower, creep, constantDamage, 0);
        return constantDamage;
    }

    @Override
    public DamageInfo rollDamage(Tower tower) {
        DamageInfo damageInfo = new DamageInfo();
        damageInfo.damage = constantDamage;
        damageInfo.multicrits = 0;
        return damageInfo;
    }
}