package com.mazebert.simulation;

public strictfp class Path {
    private final float[] points;
    private final int size;

    public Path(float ... points) {
        if (points.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid point coordinate amount");
        }
        this.points = points;
        this.size = points.length / 2;
    }

    public float[] get(int pathIndex, float[] result) {
        result[0] = points[pathIndex * 2];
        result[1] = points[pathIndex * 2 + 1];
        return result;
    }

    public int size() {
        return size;
    }
}
