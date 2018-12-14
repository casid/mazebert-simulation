package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.projectiles.OnProjectileImpact;
import com.mazebert.simulation.projectiles.Projectile;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ProjectileDamageAbility extends Ability<Tower> implements OnAttackListener, OnProjectileImpact {
    private final DamageSystem damageSystem = Sim.context().damageSystem;
    private final ProjectileGateway projectileGateway = Sim.context().projectileGateway;
    private final ProjectileViewType viewType;
    private float speed;

    public ProjectileDamageAbility() {
        this(null, 1.0f);
    }

    public ProjectileDamageAbility(ProjectileViewType viewType, float speed) {
        this.viewType = viewType;
        this.speed = speed;
    }

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
        projectileGateway.shoot(viewType, getUnit(), target, speed, this);
    }

    @Override
    public void onProjectileImpact(Projectile projectile) {
        if (isDisposed()) {
            return;
        }
        damageSystem.dealDamage(this, getUnit(), (Creep) projectile.target);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
