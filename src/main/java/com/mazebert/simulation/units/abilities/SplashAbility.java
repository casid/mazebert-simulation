package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SplashAbility extends Ability<Tower> implements OnDamageListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final FormatPlugin formatPlugin = Sim.context().formatPlugin;

    private int range;
    private float damageFactor;

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
    public void onDamage(Object origin, Creep target, double damage) {
        if (origin == this) {
            return;
        }

        double splashDamage = StrictMath.max(1.0, getDamageFactor() * damage);
        unitGateway.forEachInRange(target.getX(), target.getY(), getRange(), Creep.class, creep -> {
            if (creep != target) {
                creep.receiveDamage(splashDamage);
                getUnit().onDamage.dispatch(this, creep, damage);
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
        return "Creeps in " + range + " range around the target receive " + formatPlugin.percent(damageFactor) + "% damage.";
    }

    public String getIconFile() {
        return "0036_magicgizmo_512";
    }
}
