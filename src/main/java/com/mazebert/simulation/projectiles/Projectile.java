package com.mazebert.simulation.projectiles;

import com.mazebert.simulation.units.Unit;

public strictfp class Projectile {
    public float x;
    public float y;
    public float speed;
    public float srcX;
    public float srcY;
    public Unit source;
    public Unit target;
    public boolean added;
    public volatile boolean freshCoordinates;
    public OnProjectileImpact onImpact;

    // For game display usage
    public transient ProjectileView view;

    public boolean update(float dt) {
        if (added) {
            added = false;
            return true;
        }

        float dx = target.getX() - x;
        float dy = target.getY() - y;

        float distance = (float) StrictMath.sqrt(dx * dx + dy * dy);
        float distanceToTravel = speed * dt;

        if (distance > distanceToTravel) {
            distance = distanceToTravel / distance;
            dx *= distance;
            dy *= distance;

            x += dx;
            y += dy;

            freshCoordinates = true;

            return true;
        } else {
            onImpact.onProjectileImpact(this);
            return false;
        }
    }

    public void clearReferences() {
        if (view != null) {
            view.onProjectileRemoved();
            view = null;
        }

        source = null;
        target = null;
        onImpact = null;
    }
}
