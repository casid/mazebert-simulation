package com.mazebert.simulation.projectiles;

import com.mazebert.simulation.units.Unit;

public strictfp class Projectile {
    public float x;
    public float y;
    public float speed;
    public Unit target;
    public OnProjectileImpact onImpact;

    public boolean update(float dt) {
        float dx = target.getX() - x;
        float dy = target.getY() - y;

        float distance = (float)StrictMath.sqrt(dx * dx + dy * dy);
        float distanceToTravel = speed * dt;

        if (distance > distanceToTravel) {
            distance = distanceToTravel / distance;
            dx *= distance;
            dy *= distance;

            x += dx;
            y += dy;

            return true;
        } else {
            onImpact.onProjectileImpact(this);
            return false;
        }
    }

    public void clearReferences() {
        target = null;
        onImpact = null;
    }
}
