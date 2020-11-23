package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchChainsawEffect extends Ability<Creep> implements OnUpdateListener {

    private static final float DPS_UPDATE_INTERVAL = 1;

    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private final Tower tower;
    private final float damageFactor;
    private final float damageFactorPerLevel;

    private double dps;
    private float nextDpsUpdate = DPS_UPDATE_INTERVAL;

    public EldritchChainsawEffect(Tower tower, float damageFactor, float damageFactorPerLevel) {
        this.tower = tower;
        this.damageFactor = damageFactor;
        this.damageFactorPerLevel = damageFactorPerLevel;

        calculateDps();

        setOrigin(this.tower);
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
            updateDpsIfRequired(dt);
            damageSystem.dealDamage(this, tower, getUnit(), dps * dt, 0, true);
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
        float damageFactor = this.damageFactor + tower.getLevel() * damageFactorPerLevel;

        DamageSystem.DamageInfo damageInfo = damageSystem.rollDamage(tower);
        dps = (damageFactor * damageInfo.damage) / tower.getCooldown();
    }
}
