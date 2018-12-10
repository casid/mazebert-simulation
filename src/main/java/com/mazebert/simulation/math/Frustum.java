package com.mazebert.simulation.math;

public strictfp class Frustum {
    public final Plane[] planes;

    public Frustum(int planesCount) {
        planes = new Plane[planesCount];
        for (int i = 0; i < planesCount; ++i) {
            planes[i] = new Plane();
        }
    }

    public final boolean containsPoint(float px, float py, float pz) {
        for (Plane p : planes) {
            float distance = p.a * px + p.b * py + p.c * pz + p.d;
            if (distance > 0.0) {
                return false;
            }
        }

        return true;
    }
}
