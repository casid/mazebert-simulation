package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.OnProjectileImpact;
import com.mazebert.simulation.projectiles.Projectile;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ProjectileDamageAbility extends DamageAbility implements OnProjectileImpact {
    private final ProjectileGateway projectileGateway = Sim.context().projectileGateway;
    private float speed;

    public ProjectileDamageAbility() {
        this(1.0f);
    }

    public ProjectileDamageAbility(float speed) {
        this.speed = speed;
    }

    @Override
    public void onAttack(Creep target) {
        projectileGateway.shoot(getUnit(), target, speed, this);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void onProjectileImpact(Projectile projectile) {
        dealDamage((Creep) projectile.target);
    }
}
