package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.PoisonEffect;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SplashAbility extends Ability<Tower> implements OnDamageListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private int range;
    private float damageFactor;

    public SplashAbility() {
    }

    public SplashAbility(int range, float damageFactor) {
        this.range = range;
        this.damageFactor = damageFactor;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

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
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (origin instanceof SplashAbility || origin instanceof PoisonEffect) {
            return;
        }

        double splashDamage = StrictMath.max(1.0, getDamageFactor() * damage);
        unitGateway.forEachCreep(creep -> {
            if (creep != target && creep.isInRange(target, range)) {
                damageSystem.dealDamage(this, getUnit(), creep, splashDamage, 0, false);
            }
        });
    }

    public boolean isVisibleToUser() {
        return true;
    }

    public String getTitle() {
        return "Splash";
    }

    public String getDescription() {
        return "Creeps within " + range + " range of the target receive " + format.percent(damageFactor) + "% damage.";
    }

    public String getIconFile() {
        return "0036_magicgizmo_512";
    }
}
