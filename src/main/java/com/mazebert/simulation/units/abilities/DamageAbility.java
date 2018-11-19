package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DamageAbility extends Ability<Tower> implements OnAttackListener {
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        dealDamage(target);
    }

    protected void dealDamage(Creep target) {
        if (target.isDead()) {
            return;
        }

        Tower tower = getUnit();
        double baseDamage = randomPlugin.getDouble(tower.getMinBaseDamage(), tower.getMaxBaseDamage());
        baseDamage += tower.getAddedAbsoluteBaseDamage();
        baseDamage = baseDamage * (1.0 + tower.getAddedRelativeBaseDamage());
        double damage = baseDamage;

        float critChance = tower.getCritChance();

        for (int i = 0; i < tower.getMulticrit(); ++i) {
            if (randomPlugin.getFloatAbs() < critChance) {
                damage += baseDamage * tower.getCritDamage();
                critChance *= 0.8f;
            } else {
                break;
            }
        }

        target.receiveDamage(damage);
        tower.onDamage.dispatch(this, target, damage);

        if (target.isDead()) {
            tower.onKill.dispatch(target);
        }
    }
}
