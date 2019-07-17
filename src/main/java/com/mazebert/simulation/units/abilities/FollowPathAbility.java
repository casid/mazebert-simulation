package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.maps.FollowPath;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class FollowPathAbility<U extends Unit> extends Ability<U> implements OnUpdateListener {
    private final FollowPathResult followPathResult = Sim.context().followPathResult;

    private boolean freshCoordinates;
    private boolean forceFreshCoordinates;
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
            followPath(dt, false);
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

    public final void followPath(float dt, boolean forceFreshCoordinates) {
        float distanceToWalk = getSpeed() * dt;
        followPathDistance(distanceToWalk, forceFreshCoordinates);
    }

    public final void followPathDistance(float distanceToWalk, boolean forceFreshCoordinates) {
        followPathResult.pathIndex = pathIndex;
        FollowPathResult result = followPath(getUnit().getX(), getUnit().getY(), distanceToWalk, followPathResult);
        if (result != null) {
            pathIndex = result.pathIndex;
            getUnit().setX(result.px);
            getUnit().setY(result.py);

            freshCoordinates = true;
            if (forceFreshCoordinates) {
                this.forceFreshCoordinates = true;
            }

            if (pathIndex >= path.size() - 1) {
                onTargetReached();
            }
        }
    }

    public final FollowPathResult predict(float x, float y, float dt, FollowPathResult result) {
        if (isDisposed()) {
            return null;
        }

        if (dt == 0) {
            return null;
        }

        if (freshCoordinates) {
            freshCoordinates = false;

            if (forceFreshCoordinates || StrictMath.abs(x - getUnit().getX()) > 1 || StrictMath.abs(y - getUnit().getY()) > 1) {
                forceFreshCoordinates = false;

                x = getUnit().getX();
                y = getUnit().getY();
                result.pathIndex = pathIndex;
            }
        }

        float distanceToWalk = getSpeed() * dt;

        if (result.pathIndex == -1) {
            result.pathIndex = pathIndex;
        }

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
