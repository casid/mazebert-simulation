package com.mazebert.simulation.units;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Supplier;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.util.SafeIterationArray;

public abstract strictfp class Unit implements Hashable {

    public final OnUnitAdded onUnitAdded = new OnUnitAdded(); // Only dispatched when this unit is added to the game
    public final OnUnitRemoved onUnitRemoved = new OnUnitRemoved(); // Only dispatched when this unit is removed from the game
    public final OnAbilityAdded onAbilityAdded = new OnAbilityAdded();
    public final OnAbilityRemoved onAbilityRemoved = new OnAbilityRemoved();
    public final OnUpdate onUpdate = new OnUpdate();

    // For game display usage
    @SuppressWarnings("unused")
    public transient UnitView view;

    // Internal flag for simulation usage
    public transient boolean visited;

    // Fast lookup, if this unit has consumed a permanent ability
    private transient boolean permanentAbility;

    private Wizard wizard; // The wizard this unit belongs to
    private float x;
    private float y;

    private SafeIterationArray<Ability> abilities = new SafeIterationArray<>();

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

    public final float getX() {
        return x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final float getY() {
        return y;
    }

    public final void setY(float y) {
        this.y = y;
    }

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
        }
        ability.addStack();

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

    @SuppressWarnings("unchecked")
    public <T extends StackableByOriginAbility> T addAbilityStack(Object origin, Class<T> abilityClass, Supplier<T> supplier) {
        T result = (T)abilities.find(a -> a.getClass() == abilityClass && a.getOrigin() == origin);

        if (result == null) {
            result = supplier.get();
            addAbilityInternal(result);
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

    public <T extends StackableAbility> void removeAllAbilityStacks(T ability) {
        ability.removeAllStacks();
        removeAbilityInternal(ability);
    }

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public <T extends StackableByOriginAbility> T removeAbilityStack(Object origin, Class<T> abilityClass) {
        T ability = (T)abilities.find(a -> a.getClass() == abilityClass && a.getOrigin() == origin);

        if (ability != null) {
            removeAbilityInternal(ability);
        }

        return ability;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ability> T removeAbility(Class<T> abilityClass) {
        T ability = (T)abilities.find(a -> a.getClass() == abilityClass);

        if (ability != null) {
            removeAbilityInternal(ability);
        }

        return ability;
    }

    public <T extends Ability> T removeAbility(Class<T> abilityClass, Object origin) {
        T ability = getAbility(abilityClass, origin);
        if (ability != null) {
            removeAbility(ability);
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
        if (!permanentAbility && ability.isPermanent()) {
            permanentAbility = true;
        }

        abilities.add(ability);
        ability.init(this);

        onAbilityAdded.dispatch(ability);
    }

    private void removeAbilityInternal(Ability ability) {
        abilities.remove(ability);
        ability.dispose();

        onAbilityRemoved.dispatch(ability);
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
        return StrictMath.abs(StrictMath.round(this.x) - StrictMath.round(x)) <= range && StrictMath.abs(StrictMath.round(this.y) - StrictMath.round(y)) <= range;
    }

    @SuppressWarnings("unused") // Used by client
    public abstract String getModelId();

    public void dispose() {
        abilities.forEachIndexed((index, ability) -> {
            ability.dispose();
            abilities.remove(index);
        });
        abilities = null;
    }

    public boolean isDisposed() {
        return abilities == null;
    }

    public float getGoldModifier() {
        return 1;
    }

    public boolean hasPermanentAbility() {
        return permanentAbility;
    }
}
