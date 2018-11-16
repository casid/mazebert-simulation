package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.OnDead;
import com.mazebert.simulation.listeners.OnDeath;
import com.mazebert.simulation.listeners.OnTargetReached;
import com.mazebert.simulation.units.Unit;

public strictfp class Creep extends Unit {
    public static final float DEATH_TIME = 2.0f;

    private static final WalkResult TEMP = new WalkResult();

    public final OnDeath onDeath = new OnDeath();
    public final OnDead onDead = new OnDead();
    public final OnTargetReached onTargetReached = new OnTargetReached();

    private double health = 100.0f;
    private double maxHealth = health;
    private Wave wave;
    private float baseSpeed = 1.0f;
    private float speedModifier = 1.0f;
    private Path path;
    private int pathIndex;
    private CreepState state = CreepState.Running;
    private int gold;
    private int armor;

    private transient float deathTime;
    private transient volatile boolean freshCoordinates;


    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(health);
        hash.add(maxHealth);
        hash.add(wave);
        hash.add(baseSpeed);
        hash.add(speedModifier);
        // ignore path
        // ignore pathIndex
        hash.add(state);
        hash.add(gold);
        hash.add(armor);
    }

    @Override
    public void simulate(float dt) {
        super.simulate(dt);

        switch (state) {
            case Running:
                float distanceToWalk = getSpeed() * dt;
                TEMP.pathIndex = pathIndex;
                WalkResult result = walk(getX(), getY(), distanceToWalk, TEMP);
                if (result != null) {
                    pathIndex = result.pathIndex;
                    setX(result.getX());
                    setY(result.getY());

                    freshCoordinates = true;

                    if (pathIndex >= path.size() - 1) {
                        onTargetReached.dispatch(this);
                    }
                }
                break;
            case Death:
                deathTime += dt;
                if (deathTime >= DEATH_TIME) {
                    setState(CreepState.Dead);
                    onDead.dispatch(this);
                }
                break;
        }
    }

    private WalkResult walk(float x, float y, float distanceToWalk, WalkResult result) {
        if (state != CreepState.Running) {
            return null;
        }

        if (distanceToWalk <= 0.0f) {
            return null;
        }

        int targetIndex = result.pathIndex + 1;
        if (targetIndex >= path.size()) {
            return null;
        }

        float[] target = path.get(targetIndex, result.position);
        float tx = target[0];
        float ty = target[1];

        float dx = tx - x;
        float dy = ty - y;

        float distance = (float)StrictMath.sqrt(dx * dx + dy * dy);
        if (distance <= distanceToWalk) {
            result.position[0] = tx;
            result.position[1] = ty;
            ++result.pathIndex;

            walk(tx, ty, distanceToWalk - distance, result);
        } else {
            result.position[0] = x + distanceToWalk * dx / distance;
            result.position[1] = y + distanceToWalk * dy / distance;
        }

        return result;
    }

    public WalkResult predictWalk(float x, float y, float dt, WalkResult result) {
        if (freshCoordinates) {
            freshCoordinates = false;
            x = getX();
            y = getY();
        }

        float distanceToWalk = getSpeed() * dt;

        result.pathIndex = pathIndex;
        if (walk(x, y, distanceToWalk, result) != null) {
            float[] direction = path.get(result.pathIndex, result.direction);
            direction[0] = result.position[0] - direction[0];
            direction[1] = result.position[1] - direction[1];
        }

        return result;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if (this.health > 0.0) {
            if (health <= 0.0) {
                this.health = 0.0;
                deathTime = 0.0f;
                setState(CreepState.Death);
                onDeath.dispatch(this);
            } else {
                this.health = health;
            }
        }
    }

    public boolean isDead() {
        return health <= 0.0f;
    }

    @Override
    public boolean isInRange(float x, float y, float range) {
        return !isDead() && super.isInRange(x, y, range);
    }

    @Override
    public String getModelId() {
        return "orc"; // TODO
    }

    public void setWave(Wave wave) {
        this.wave = wave;
    }

    public Wave getWave() {
        return wave;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    public float getSpeed() {
        return baseSpeed * speedModifier;
    }

    public void setPath(Path path) {
        this.path = path;
        if (path != null && path.size() > 0) {
            setX(path.getX(0));
            setY(path.getY(0));
        }
    }

    public void setState(CreepState state) {
        this.state = state;
    }

    public CreepState getState() {
        return state;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void receiveDamage(double damage) {
        setHealth(getHealth() - damage);
    }

    public static class WalkResult {
        private float[] position = new float[2];
        private float[] direction = new float[2];
        private int pathIndex;

        public float getX() {
            return position[0];
        }

        public float getY() {
            return position[1];
        }

        public float getDx() {
            return direction[0];
        }

        public float getDy() {
            return direction[1];
        }
    }
}
