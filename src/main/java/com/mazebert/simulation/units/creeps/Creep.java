package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.FollowPathCreepAbility;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.util.DamageMap;

public strictfp class Creep extends Unit {
    public static final float DEATH_TIME = 2.0f;

    public final OnDeath onDeath = new OnDeath();
    public final OnDead onDead = new OnDead();
    public final OnResurrect onResurrect = new OnResurrect();
    public final OnTargetReached onTargetReached = new OnTargetReached();
    public final OnCreepHealthChanged onHealthChanged = new OnCreepHealthChanged();
    public final OnDamage onDamage = new OnDamage();

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
    private float immobilizeResistance = 0;
    private float chanceToMiss = 0;

    private transient double initialHealth;
    private transient float deathTime;
    private transient final FollowPathCreepAbility followPathAbility;
    private transient final DamageMap damageMap = new DamageMap();
    private transient boolean steady;
    private transient boolean immortal;
    private transient boolean restsInPiece;

    public Creep() {
        this(new FollowPathCreepAbility());
    }

    public Creep(FollowPathCreepAbility followPathAbility) {
        this.followPathAbility = followPathAbility;
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

        if (immobilizeResistance > 0) {
            immobilizeResistance -= 0.1f * dt;
            if (immobilizeResistance < 0) {
                immobilizeResistance = 0;
            }
        }
    }

    public FollowPathResult predictWalk(float x, float y, float dt, FollowPathResult result) {
        return followPathAbility.predict(x, y, dt, result);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        setHealth(null, health, false);
    }

    public void setHealth(Tower tower, double health, boolean notify) {
        if (this.health > 0.0 && !isImmortal()) {
            double oldHealth = this.health;

            if (health <= 0.0) {
                this.health = 0.0;
                deathTime = 0.0f;
                setState(CreepState.Death);
                onDeath.dispatch(this);
            } else if (health > maxHealth) {
                this.health = maxHealth;
            } else {
                this.health = health;
            }

            if (tower != null) {
                damageMap.add(tower, oldHealth - health);
            }

            if (notify) {
                onHealthChanged.dispatch(tower, this, oldHealth, this.health);
            }
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
        this.initialHealth = maxHealth;
        this.health = maxHealth;
    }

    public double getInitialHealth() {
        return initialHealth;
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

    public void knockback(float distance) {
        if (!steady && isPartOfGame()) {
            if (distance > 0) {
                addImmobilizeResistance(0.1f);
            }
            if (Sim.context().version >= Sim.v19) {
                followPathAbility.followPathDistance(-distance, true);
            } else {
                followPathAbility.followPath(-distance, true);
            }
        }
    }

    public void warpInTime(float warpSeconds) {
        if (!steady && isPartOfGame()) {
            if (warpSeconds < 0) {
                addImmobilizeResistance(0.1f);
            }
            followPathAbility.followPath(warpSeconds, true);
        }
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

    public void setSteady(boolean steady) {
        this.steady = steady;
    }

    public boolean isSteady() {
        return steady;
    }

    public float getImmobilizeResistance() {
        return immobilizeResistance;
    }

    public void addImmobilizeResistance(float immobilizeResistance) {
        this.immobilizeResistance += immobilizeResistance;
    }

    public void addChanceToMiss(float chanceToMiss) {
        this.chanceToMiss += chanceToMiss;
    }

    public float getChanceToMiss() {
        return chanceToMiss;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public float getTargetX() {
        return followPathAbility.getTargetX();
    }

    public float getTargetY() {
        return followPathAbility.getTargetY();
    }

    public void setRestsInPiece(boolean restsInPiece) {
        this.restsInPiece = restsInPiece;
    }

    public boolean isRestsInPiece() {
        return restsInPiece;
    }

    @Override
    public void setWizard(Wizard wizard) {
        // After DoL, creeps do not have a specific wizard!
        if (Sim.context().version < Sim.vDoLEnd) {
            super.setWizard(wizard);
        }
    }

    public DamageMap getDamageMap() {
        return damageMap;
    }

    public boolean isBoss() {
        switch (wave.type) {
            case Boss:
            case Challenge:
            case Horseman:
            case TimeLord:
                return true;
        }

        return false;
    }

    public boolean isAir() {
        switch (wave.type) {
            case Air:
            case CultistOfAzathoth:
                return true;
        }

        return false;
    }

    public boolean isMass() {
        switch (wave.type) {
            case Mass:
            case CultistOfYig:
                return true;
        }

        return false;
    }

    public boolean isEldritch() {
        return wave.type.isEldritch();
    }
}
