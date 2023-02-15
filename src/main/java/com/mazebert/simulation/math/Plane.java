package com.mazebert.simulation.math;

@SuppressWarnings("unused")
public final strictfp class Plane {
    public float a;
    public float b;
    public float c;
    public float d;

    /**
     * sets up the coefficients of the plane
     */
    public void setCoefficients(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * sets up the coefficients of the plane
     * @param px x coord of a point on the plane
     * @param py y coord of a point on the plane
     * @param pz z coord of a point on the plane
     * @param nx x direction of the normal of the plane
     * @param ny y direction of the normal of the plane
     * @param nz z direction of the normal of the plane
     */
    public void setPointAndDirection(float px, float py, float pz, float nx, float ny, float nz) {
        a = nx;
        b = ny;
        c = nz;
        d = -(nx * px + ny * py + nz * pz);
    }

    /**
     * sets this plane to the values of p
     */
    public void setPlane(Plane p) {
        a = p.a;
        b = p.b;
        c = p.c;
        d = p.d;
    }

    /**
     * normalizes the plane
     */
    public Plane normalize() {
        float length = (float)StrictMath.sqrt(a * a + b * b + c * c);

        a /= length;
        b /= length;
        c /= length;
        d /= length;

        return this;
    }

    /**
     * @return the nearest distance form a point to the plane
     */
    public float distanceToPoint(float x, float y, float z) {
        return a * x + b * y + c * z + d;
    }
}
