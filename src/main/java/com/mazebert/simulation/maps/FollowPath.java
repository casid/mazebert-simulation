package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;

public class FollowPath {

    public static FollowPathResult followPath(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        if (distanceToWalk >= 0.0f) {
            return forward(x, y, distanceToWalk, path, result);
        } else {
            return backward(x, y, distanceToWalk, path, result);
        }
    }

    @SuppressWarnings("Duplicates")
    public static FollowPathResult forward(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        if (distanceToWalk <= 0.0f) {
            return null;
        }

        if (path == null) {
            return null;
        }

        int targetIndex = result.pathIndex + 1;
        if (targetIndex >= path.size()) {
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
    public static FollowPathResult backward(float x, float y, float distanceToWalk, Path path, FollowPathResult result) {
        if (distanceToWalk >= 0.0f) {
            return null;
        }

        int targetIndex = result.pathIndex;
        if (targetIndex < 0) {
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
}
