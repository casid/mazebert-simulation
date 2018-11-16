package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SplashDamageAbility extends DamageAbility {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final FormatPlugin formatPlugin = Sim.context().formatPlugin;

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
