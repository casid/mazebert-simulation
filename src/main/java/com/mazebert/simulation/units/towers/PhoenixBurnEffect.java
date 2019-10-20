package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PhoenixBurnEffect extends Ability<Creep> implements OnUpdateListener {

    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private final Tower phoenix;
    private final double damagePerSecond;

    public PhoenixBurnEffect(Tower phoenix) {
        this.phoenix = phoenix;
        DamageSystem.DamageInfo damageInfo = damageSystem.rollDamage(phoenix);

        this.damagePerSecond = damageInfo.damage / phoenix.getCooldown();

        setOrigin(phoenix);
    }

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        if (getUnit().isDead()) {
            getUnit().removeAbility(this);
        } else {
            damageSystem.dealDamage(this, phoenix, getUnit(), damagePerSecond * dt, 0, true);
        }
    }
}
