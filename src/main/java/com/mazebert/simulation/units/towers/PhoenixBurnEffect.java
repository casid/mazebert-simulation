package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PhoenixBurnEffect extends Ability<Creep> implements OnUpdateListener {

    private static final float DPS_UPDATE_INTERVAL = 1;

    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private final Tower phoenix;
    private final PhoenixRebirth phoenixRebirth;

    private double dps;
    private float nextDpsUpdate = DPS_UPDATE_INTERVAL;

    public PhoenixBurnEffect(Tower phoenix) {
        this.phoenix = phoenix;
        this.phoenixRebirth = phoenix.getAbility(PhoenixRebirth.class);

        calculateDps();

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
        } else if (phoenixRebirth.isAlive()) {
            updateDpsIfRequired(dt);
            damageSystem.dealDamage(this, phoenix, getUnit(), dps * dt, 0, true);
        }
    }

    private void updateDpsIfRequired(float dt) {
        nextDpsUpdate -= dt;
        if (nextDpsUpdate <= 0) {
            calculateDps();
            nextDpsUpdate = DPS_UPDATE_INTERVAL;
        }
    }

    private void calculateDps() {
        DamageSystem.DamageInfo damageInfo = damageSystem.rollDamage(phoenix);
        dps = damageInfo.damage / phoenix.getCooldown();
    }
}
