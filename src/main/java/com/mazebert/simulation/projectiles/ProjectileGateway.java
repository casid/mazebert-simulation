package com.mazebert.simulation.projectiles;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.util.ObjectPool;

public strictfp class ProjectileGateway {
    private ObjectPool<Projectile> pool = new ObjectPool<>(Projectile::new, Projectile.class, 10);

    public int getSize() {
        return pool.getActiveSize();
    }

    public void shoot(Unit src, Creep target, float speed, OnProjectileImpact onImpact) {
        Projectile projectile = pool.add();
        projectile.x = src.getX();
        projectile.y = src.getY();
        projectile.speed = speed;
        projectile.target = target;
        projectile.onImpact = onImpact;
    }

    public Projectile get(int index) {
        return pool.getActive()[index];
    }

    public void update(float dt) {
        Projectile[] projectiles = pool.getActive();
        int size = pool.getActiveSize();
        for (int i = 0; i < size; ++i) {
            if (!projectiles[i].update(dt)) {
                projectiles[i].clearReferences();
                pool.recycle(projectiles[i]);
                --i;
                --size;
            }
        }
    }
}
