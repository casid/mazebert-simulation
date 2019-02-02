package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.FollowPathCreepAbility;

public strictfp class Creep extends Unit {
    public static final float DEATH_TIME = 2.0f;

    public final OnDeath onDeath = new OnDeath();
    public final OnDead onDead = new OnDead();
    public final OnResurrect onResurrect = new OnResurrect();
    public final OnTargetReached onTargetReached = new OnTargetReached();
    public final OnHealthChanged onHealthChanged = new OnHealthChanged();

    private double health = 100.0;
    private double maxHealth = health;
    private Wave wave;
    private float baseSpeed = 1.0f;
    private float speedModifier = 1.0f;
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
    private transient final FollowPathCreepAbility followPathAbility = new FollowPathCreepAbility();

    public Creep() {
        addAbility(followPathAbility);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(health);
        hash.add(maxHealth);
        hash.add(wave);
        hash.add(baseSpeed);
        hash.add(speedModifier);
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

        if (state == CreepState.Death) {
            deathTime += dt;
            if (deathTime >= DEATH_TIME) {
                setState(CreepState.Dead);
                onDead.dispatch(this);
            }
        }
    }

    @SuppressWarnings("unused") // Used by client
    public FollowPathResult predictWalk(float x, float y, float dt, FollowPathResult result) {
        return followPathAbility.predict(x, y, dt, result);
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
        this.experience = 0;
        this.gold = 0;
        this.maxDrops = 0;
        this.minDrops = 0;
        setState(CreepState.Running);
        onResurrect.dispatch(this);
    }

    public boolean isDead() {
        return health <= 0.0f;
    }

    @Override
    public boolean isInRange(float x, float y, float range) {
        return isPartOfGame() && super.isInRange(x, y, range);
    }

    public boolean isPartOfGame() {
        return !isDead() && !isDisposed();
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
        followPathAbility.setPath(path, index);
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
        followPathAbility.followPath(warpSeconds);
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
}
