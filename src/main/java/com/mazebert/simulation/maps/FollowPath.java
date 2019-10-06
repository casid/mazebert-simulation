package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;

public strictfp class FollowPath {

    public static FollowPathResult followPath(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        if (distanceToWalk >= 0.0f) {
            return forward(x, y, distanceToWalk, path, result);
        } else {
            return backward(x, y, distanceToWalk, path, result);
        }
    }

    @SuppressWarnings("Duplicates")
    private static FollowPathResult forward(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        if (path == null) {
            return null;
        }

        int targetIndex = result.pathIndex + 1;
        if (targetIndex >= path.size()) {
            return null;
        }

        if (distanceToWalk <= 0.0f) {
            return null;
        }

        float tx = path.getX(targetIndex);
        float ty = path.getY(targetIndex);

        float dx = tx - x;
        float dy = ty - y;

        float distance = (float) StrictMath.sqrt(dx * dx + dy * dy);
        if (distance <= distanceToWalk) {
            result.px = tx;
            result.py = ty;
            ++result.pathIndex;

            forward(tx, ty, distanceToWalk - distance, path, result);
        } else {
            result.px = x + distanceToWalk * dx / distance;
            result.py = y + distanceToWalk * dy / distance;
        }

        return result;
    }

    @SuppressWarnings("Duplicates")
    private static FollowPathResult backward(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        int targetIndex = result.pathIndex;
        if (targetIndex < 0) {
            result.pathIndex = 0;
            return null;
        }

        if (distanceToWalk >= 0.0f) {
            return null;
        }

        float tx = path.getX(targetIndex);
        float ty = path.getY(targetIndex);

        float dx = tx - x;
        float dy = ty - y;

        float distance = (float) StrictMath.sqrt(dx * dx + dy * dy);
        if (distance <= -distanceToWalk) {
            result.px = tx;
            result.py = ty;
            --result.pathIndex;

            backward(tx, ty, distanceToWalk + distance, path, result);
        } else {
            result.px = x - distanceToWalk * dx / distance;
            result.py = y - distanceToWalk * dy / distance;
        }

        return result;
    }

    public static FollowPathResult findClosestPointOnPath(float x, float y, Path path) {
        FollowPathResult result = new FollowPathResult();
        float bestDistance = Float.MAX_VALUE;

        for (int i = 0; i < path.size() - 1; ++i) {
            float ax = path.getX(i);
            float ay = path.getY(i);
            float bx = path.getX(i + 1) - ax;
            float by = path.getY(i + 1) - ay;
            float bl = (float) StrictMath.sqrt(bx * bx + by * by);

            float distance = ((x - ax) * by - (y - ay) * bx) / bl;
            if (StrictMath.abs(distance) < bestDistance) {
                float rx = x - distance * by / bl;
                float ry = y + distance * bx / bl;

                float lambda = StrictMath.abs(bx) > StrictMath.abs(by) ? (rx - ax) / bx : (ry - ay) / by;
                if (lambda < 0.0f || lambda > 1.0f) {
                    if (lambda < 0.0f) {
                        ax = path.getX(i);
                        ay = path.getY(i);
                    } else {
                        ax = path.getX(i + 1);
                        ay = path.getY(i + 1);
                    }
                    rx = x - ax;
                    ry = y - ay;
                    distance = (float) StrictMath.sqrt(rx * rx + ry * ry);
                    if (distance < bestDistance) {
                        result.px = ax;
                        result.py = ay;
                        result.pathIndex = i;
                        bestDistance = distance;
                    }
                } else {
                    result.px = rx;
                    result.py = ry;
                    result.pathIndex = i;
                    bestDistance = StrictMath.abs(distance);
                }
            }
        }


        return result;
    }
}
