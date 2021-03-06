package com.mazebert.simulation.projectiles;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.util.ObjectPool;

public strictfp final class ProjectileGateway {
    private final ObjectPool<Projectile> pool = new ObjectPool<>(Projectile::new, Projectile.class, 10);

    public int getSize() {
        return pool.getActiveSize();
    }

    public void shoot(ProjectileViewType viewType, Unit source, Unit target, float speed, OnProjectileImpact onImpact) {
        Projectile projectile = pool.add();
        projectile.x = projectile.srcX = source.getX();
        projectile.y = projectile.srcY = source.getY();
        projectile.speed = speed;
        projectile.source = source;
        projectile.target = target;
        projectile.added = true;
        projectile.viewType = viewType;
        projectile.freshCoordinates = true;
        projectile.onImpact = onImpact;
    }

    public Projectile get(int index) {
        return pool.getActive()[index];
    }

    public void simulate(float dt) {
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

    @SuppressWarnings("unused") // Used by client
    public void initNewProjectiles(ProjectileInitializer initializer) {
        Projectile[] projectiles = pool.getActive();
        int size = pool.getActiveSize();
        for (int i = 0; i < size; ++i) {
            if (projectiles[i].view == null) {
                projectiles[i].view = initializer.initializeProjectile(projectiles[i]);
            }
        }
    }

    public interface ProjectileInitializer {
        ProjectileView initializeProjectile(Projectile projectile);
    }
}
