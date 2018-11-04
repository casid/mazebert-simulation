package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class SplashDamageAbility extends DamageAbility {
    @Inject
    private UnitGateway unitGateway;

    private int range;
    private float damageFactor;

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public float getDamageFactor() {
        return damageFactor;
    }

    public void setDamageFactor(float damageFactor) {
        this.damageFactor = damageFactor;
    }

    @Override
    protected void onDamage(Creep target, double damage) {
        super.onDamage(target, damage);

        double splashDamage = StrictMath.max(1.0, getDamageFactor() * damage);
        unitGateway.forEachInRange(target.getX(), target.getY(), getRange(), Creep.class, creep -> {
            if (creep != target) {
                creep.receiveDamage(splashDamage);
            }
        });
    }
}
