package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.OnDead;
import com.mazebert.simulation.listeners.OnDeath;
import com.mazebert.simulation.listeners.OnHealthChanged;
import com.mazebert.simulation.listeners.OnTargetReached;
import com.mazebert.simulation.units.Unit;

public strictfp class Creep extends Unit {
    public static final float DEATH_TIME = 2.0f;

    private static final WalkResult TEMP = new WalkResult();

    public final OnDeath onDeath = new OnDeath();
    public final OnDead onDead = new OnDead();
    public final OnTargetReached onTargetReached = new OnTargetReached();
    public final OnHealthChanged onHealthChanged = new OnHealthChanged();

    private double health = 100.0;
    private double maxHealth = health;
    private Wave wave;
    private float baseSpeed = 1.0f;
    private float speedModifier = 1.0f;
    private Path path;
    private int pathIndex;
    private CreepType type;
    private CreepState state = CreepState.Running;
    private int gold;
    private float goldModifier = 1;
    private int maxDrops;
    private int minDrops;
    private int maxItemLevel;
    private float dropChance = 1.0f;
    private int armor;
    private float damageModifier = 1.0f;
    private float experience;
    private float experienceModifier = 1.0f;

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
        hash.add(type);
        hash.add(state);
        hash.add(gold);
        hash.add(goldModifier);
        hash.add(maxDrops);
        hash.add(minDrops);
        hash.add(maxItemLevel);
        hash.add(dropChance);
        hash.add(armor);
        hash.add(damageModifier);
        hash.add(experience);
        hash.add(experienceModifier);
    }

    @Override
    public void simulate(float dt) {
        super.simulate(dt);

        switch (state) {
            case Running:
                walk(dt);
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

    private void walk(float dt) {
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
    }

    private WalkResult walk(float x, float y, float distanceToWalk, WalkResult result) {
        if (distanceToWalk >= 0.0f) {
            return walkForward(x, y, distanceToWalk, result);
        } else {
            return walkBackward(x, y, distanceToWalk, result);
        }
    }

    private WalkResult walkForward(float x, float y, float distanceToWalk, WalkResult result) {
        if (state != CreepState.Running) {
            return null;
        }

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

        float[] target = path.get(targetIndex, result.position);
        float tx = target[0];
        float ty = target[1];

        float dx = tx - x;
        float dy = ty - y;

        float distance = (float) StrictMath.sqrt(dx * dx + dy * dy);
        if (distance <= distanceToWalk) {
            result.position[0] = tx;
            result.position[1] = ty;
            ++result.pathIndex;

            walkForward(tx, ty, distanceToWalk - distance, result);
        } else {
            result.position[0] = x + distanceToWalk * dx / distance;
            result.position[1] = y + distanceToWalk * dy / distance;
        }

        return result;
    }

    private WalkResult walkBackward(float x, float y, float distanceToWalk, WalkResult result) {
        if (state != CreepState.Running) {
            return null;
        }

        if (distanceToWalk >= 0.0f) {
            return null;
        }

        int targetIndex = result.pathIndex;
        if (targetIndex < 0) {
            return null;
        }

        float[] target = path.get(targetIndex, result.position);
        float tx = target[0];
        float ty = target[1];

        float dx = tx - x;
        float dy = ty - y;

        float distance = (float) StrictMath.sqrt(dx * dx + dy * dy);
        if (distance <= -distanceToWalk) {
            result.position[0] = tx;
            result.position[1] = ty;
            --result.pathIndex;
            walkBackward(tx, ty, distanceToWalk + distance, result);
        } else {
            result.position[0] = x - distanceToWalk * dx / distance;
            result.position[1] = y - distanceToWalk * dy / distance;
        }

        return result;
    }

    @SuppressWarnings("unused") // Used by client
    public WalkResult predictWalk(float x, float y, float dt, WalkResult result) {
        if (dt == 0) {
            return null;
        }
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
            double oldHealth = this.health;

            if (health <= 0.0) {
                this.health = 0.0;
                deathTime = 0.0f;
                setState(CreepState.Death);
                onDeath.dispatch(this);
            } else {
                this.health = health;
            }

            onHealthChanged.dispatch(this, oldHealth, this.health);
        }
    }

    public void resurrect(double health) {
        this.maxHealth = health;
        this.health = health;
        setState(CreepState.Running);
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
        return type.modelId;
    }

    public CreepType getType() {
        return type;
    }

    public void setType(CreepType type) {
        this.type = type;
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
        setPath(path, 0);
    }

    public void setPath(Path path, int index) {
        this.path = path;
        if (path != null && path.size() > 0) {
            pathIndex = index;
            setX(path.getX(index));
            setY(path.getY(index));
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

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public float getExperienceModifier() {
        return experienceModifier;
    }

    public void setExperienceModifier(float experienceModifier) {
        this.experienceModifier = experienceModifier;
    }

    public int getMaxDrops() {
        return maxDrops;
    }

    public void setMaxDrops(int maxDrops) {
        this.maxDrops = maxDrops;
    }

    public int getMinDrops() {
        return minDrops;
    }

    public void setMinDrops(int minDrops) {
        this.minDrops = minDrops;
    }

    public int getMaxItemLevel() {
        return maxItemLevel;
    }

    public void setMaxItemLevel(int maxItemLevel) {
        this.maxItemLevel = maxItemLevel;
    }

    public float getDropChance() {
        return dropChance;
    }

    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }

    public float getGoldModifier() {
        return goldModifier;
    }

    public void setGoldModifier(float goldModifier) {
        this.goldModifier = goldModifier;
    }

    public void addArmor(int amount) {
        armor += amount;
    }

    public void warpInTime(float warpSeconds) {
        walk(warpSeconds);
    }

    public float getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(float damageModifier) {
        this.damageModifier = damageModifier;
    }

    public void addDamageModifier(float amount) {
        damageModifier += amount;
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
