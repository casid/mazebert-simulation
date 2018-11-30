package com.mazebert.simulation.units;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.OnUnitAdded;
import com.mazebert.simulation.listeners.OnUnitRemoved;
import com.mazebert.simulation.listeners.OnUpdate;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.util.SafeIterationArray;

public abstract strictfp class Unit implements Hashable {

    public final OnUnitAdded onUnitAdded = new OnUnitAdded(); // Only dispatched when this unit is added to the game
    public final OnUnitRemoved onUnitRemoved = new OnUnitRemoved(); // Only dispatched when this unit is removed from the game
    public final OnUpdate onUpdate = new OnUpdate();

    // For game display usage
    @SuppressWarnings("unused")
    public transient UnitView view;

    // Internal flag for simulation usage
    public transient boolean visited;

    private Wizard wizard; // The wizard this unit belongs to
    private float x;
    private float y;

    private final SafeIterationArray<Ability> abilities = new SafeIterationArray<>();

    public void simulate(float dt) {
        onUpdate.dispatch(dt);
    }

    @Override
    public void hash(Hash hash) {
        hash.add(x);
        hash.add(y);
    }

    public Wizard getWizard() {
        return wizard;
    }

    public int getPlayerId() {
        if (wizard == null) {
            return 0;
        }
        return wizard.playerId;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
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
        T ability = (T)abilities.find(a -> a.getClass() == abilityClass);

        if (ability == null) {
            ability = addAbilityByClass(abilityClass);
        } else {
            ability.addStack();
        }

        return ability;
    }

    @SuppressWarnings("unchecked")
    public <T extends StackableByOriginAbility> T addAbilityStack(Object origin, Class<T> abilityClass) {
        T result = (T)abilities.find(a -> a.getClass() == abilityClass && a.getOrigin() == origin);

        if (result == null) {
            result = addAbilityByClass(abilityClass);
            result.setOrigin(origin);
        }

        return result;
    }

    private <T extends Ability> T addAbilityByClass(Class<T> abilityClass) {
        try {
            T ability = abilityClass.newInstance();
            addAbilityInternal(ability);
            return ability;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to initialize ability " + abilityClass, e);
        }
    }

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public <T extends StackableAbility> T removeAbilityStack(Class<T> abilityClass) {
        T ability = (T)abilities.find(a -> a.getClass() == abilityClass);

        if (ability != null) {
            ability.removeStack();
            if (ability.getStackCount() <= 0) {
                removeAbilityInternal(ability);
            }
        }

        return ability;
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

    public void forEachAbility(Consumer<Ability> consumer) {
        abilities.forEach(consumer);
    }

    @SuppressWarnings("unchecked")
    public <A extends Ability> A getAbility(Class<A> abilityClass) {
        return (A)abilities.find(a -> abilityClass.isAssignableFrom(a.getClass()));
    }

    @SuppressWarnings("unchecked")
    public <A extends Ability> A getAbility(Class<A> abilityClass, Object origin) {
        return (A)abilities.find(a -> abilityClass.isAssignableFrom(a.getClass()) && a.getOrigin() == origin);
    }

    public int getAbilityCount() {
        return abilities.size();
    }

    public final boolean isInRange(Unit other, float range) {
        return isInRange(other.getX(), other.getY(), range);
    }

    public boolean isInRange(float x, float y, float range) {
        float dx = this.x - x;
        float dy = this.y - y;

        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        return dx <= range && dy <= range;
    }

    @SuppressWarnings("unused") // Used by client
    public abstract String getModelId();

    public void dispose() {
        abilities.forEachIndexed((index, ability) -> {
            ability.dispose();
            abilities.remove(index);
        });
    }
}
