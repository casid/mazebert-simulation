package com.mazebert.simulation;

import com.mazebert.simulation.math.Point;

import java.util.List;

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

    public Path(List<Point> points) {
        this.points = new float[points.size() * 2];
        this.size = points.size();

        int i = 0;
        for (Point point : points) {
            this.points[i] = point.x;
            this.points[i + 1] = point.y;
            i += 2;
        }
    }

    public float[] get(int pathIndex, float[] result) {
        result[0] = points[pathIndex * 2];
        result[1] = points[pathIndex * 2 + 1];
        return result;
    }

    public float getX(int pathIndex) {
        return points[pathIndex * 2];
    }

    public float getY(int pathIndex) {
        return points[pathIndex * 2 + 1];
    }

    public int size() {
        return size;
    }

    public float[] getPoints() {
        return points;
    }
}
