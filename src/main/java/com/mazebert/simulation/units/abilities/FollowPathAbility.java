package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class FollowPathAbility<U extends Unit> extends Ability<U> implements OnUpdateListener {
    private static final FollowPathResult TEMP = new FollowPathResult();

    private boolean freshCoordinates;
    private Path path;
    private int pathIndex;

    @Override
    protected void initialize(U unit) {
        super.initialize(unit);
        getUnit().onUpdate.add(this);
    }

    @Override
    protected void dispose(U unit) {
        getUnit().onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        if (isPossibleToWalk()) {
            followPath(dt);
        }
    }

    protected boolean isPossibleToWalk() {
        return true;
    }

    protected abstract float getSpeed();

    public final void setPath(Path path) {
        setPath(path, 0);
    }

    public final void setPath(Path path, int index) {
        this.path = path;
        if (path != null && path.size() > 0) {
            pathIndex = index;
            getUnit().setX(path.getX(index));
            getUnit().setY(path.getY(index));
        }
    }

    public final void followPath(float dt) {
        float distanceToWalk = getSpeed() * dt;
        TEMP.pathIndex = pathIndex;
        FollowPathResult result = followPath(getUnit().getX(), getUnit().getY(), distanceToWalk, TEMP);
        if (result != null) {
            pathIndex = result.pathIndex;
            getUnit().setX(result.px);
            getUnit().setY(result.py);

            freshCoordinates = true;

            if (pathIndex >= path.size() - 1) {
                onTargetReached();
            }
        }
    }

    public final FollowPathResult predict(float x, float y, float dt, FollowPathResult result) {
        if (dt == 0) {
            return null;
        }
        if (freshCoordinates) {
            freshCoordinates = false;
            x = getUnit().getX();
            y = getUnit().getY();
        }

        float distanceToWalk = getSpeed() * dt;

        result.pathIndex = pathIndex;
        if (followPath(x, y, distanceToWalk, result) != null) {
            result.dx = result.px - path.getX(result.pathIndex);
            result.dy = result.py - path.getY(result.pathIndex);
        }

        return result;
    }

    protected abstract void onTargetReached();

    private FollowPathResult followPath(float x, float y, float distanceToWalk, FollowPathResult result) {
        if (isPossibleToWalk()) {
            return FollowPath.followPath(x, y, distanceToWalk, path, result);
        } else {
            return null;
        }
    }
}
