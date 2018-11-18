package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.OnProjectileImpact;
import com.mazebert.simulation.projectiles.Projectile;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ProjectileDamageAbility extends DamageAbility implements OnProjectileImpact {
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
    public void onAttack(Creep target) {
        projectileGateway.shoot(viewType, getUnit(), target, speed, this);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void onProjectileImpact(Projectile projectile) {
        dealDamage((Creep) projectile.target);
    }
}
