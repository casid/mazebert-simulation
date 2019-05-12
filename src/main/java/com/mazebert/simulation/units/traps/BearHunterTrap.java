package com.mazebert.simulation.units.traps;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.DamageSystem.DamageHistory;
import com.mazebert.simulation.systems.DamageSystem.DamageInfo;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BearHunterTrap extends Trap {
    private final DamageSystem damageSystem = Sim.context().damageSystem;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    private final Tower origin;
    private final DamageHistory damageHistory = new DamageHistory();
    private final DamageInfo damageInfo = new DamageInfo();

    public BearHunterTrap(Tower origin) {
        this.origin = origin;
        addAbility(new BearHunterTrapAura());
    }

    public Tower getOrigin() {
        return origin;
    }

    @Override
    public void addStack() {
        super.addStack();
        DamageInfo damageInfo = damageSystem.rollDamage(origin);
        if (version >= Sim.v13) {
            this.damageInfo.damage += damageInfo.damage;
            this.damageInfo.multicrits = StrictMath.max(this.damageInfo.multicrits, damageInfo.multicrits);
        } else {
            damageHistory.add(damageInfo);
        }
    }

    public void trigger(Creep creep) {
        if (version >= Sim.v13) {
            damageSystem.dealDamage(this, origin, creep, damageInfo.damage, damageInfo.multicrits, true);
        } else {
            for (int i = 0; i < damageHistory.size(); ++i) {
                DamageInfo damageInfo = damageHistory.get(i);
                damageSystem.dealDamage(this, origin, creep, damageInfo.damage, damageInfo.multicrits, true);
            }
        }

        unitGateway.removeUnit(this);
    }

    @Override
    public String getModelId() {
        return "trap";
    }
}
