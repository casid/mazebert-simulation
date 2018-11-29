package com.mazebert.simulation.units;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.OnUpdate;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;

import java.util.ArrayList;
import java.util.List;

public abstract strictfp class Unit implements Hashable {

    public final OnUpdate onUpdate = new OnUpdate();

    // For game display usage
    public transient UnitView view;

    private int playerId; // The player this unit belongs to
    private float x;
    private float y;

    private final List<Ability> abilities = new ArrayList<>();

    public void simulate(float dt) {
        onUpdate.dispatch(dt);
    }

    @Override
    public void hash(Hash hash) {
        hash.add(playerId);
        hash.add(x);
        hash.add(y);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @SuppressWarnings("unchecked")
    public void addAbility(Ability ability) {
        if (ability instanceof StackableAbility) {
            addAbilityStack(((StackableAbility) ability).getClass());
        } else {
            addAbilityInternal(ability);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends StackableAbility> T addAbilityStack(Class<T> abilityClass) {
        for (Ability ability : abilities) {
            if (ability.getClass() == abilityClass) {
                T result = (T) ability;
                result.addStack();
                return result;
            }
        }
        try {
            T ability = abilityClass.newInstance();
            addAbilityInternal(ability);
            return ability;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to initialize", e);
        }
    }

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public <T extends StackableAbility> T removeAbilityStack(Class<T> abilityClass) {
        for (Ability ability : abilities) {
            if (ability.getClass() == abilityClass) {
                T result = (T) ability;
                result.removeStack();
                if (result.getStackCount() <= 0) {
                    removeAbilityInternal(ability);
                }
                return result;
            }
        }
        return null;
    }

    public void removeAbility(Ability ability) {
        if (ability instanceof StackableAbility) {
            removeAbilityStack(((StackableAbility) ability).getClass());
        } else {
            removeAbilityInternal(ability);
        }
    }

    @SuppressWarnings("unchecked")
    private void addAbilityInternal(Ability ability) {
        abilities.add(ability);
        ability.init(this);
    }

    private void removeAbilityInternal(Ability ability) {
        ability.dispose();
        abilities.remove(ability);
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public Ability getAbility(Class<? extends Ability> abilityClass) {
        for (Ability ability : abilities) {
            if (abilityClass.isAssignableFrom(ability.getClass())) {
                return ability;
            }
        }
        return null;
    }

    public final boolean isInRange(Unit other, float range) {
        return isInRange(other.getX(), other.getY(), range);
    }

    public boolean isInRange(float x, float y, float range) {
        float dx = StrictMath.abs(this.x - x);
        float dy = StrictMath.abs(this.y - y);

        return dx <= range && dy <= range;
    }

    public abstract String getModelId();
}
