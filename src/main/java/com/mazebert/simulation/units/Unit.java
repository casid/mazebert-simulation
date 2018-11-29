package com.mazebert.simulation.units;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.OnUpdate;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public abstract strictfp class Unit implements Hashable {

    // This works since the simulation runs single threaded
    private static final List<Ability> ABILITIES_TO_DISPOSE = new ArrayList<>();

    public final OnUpdate onUpdate = new OnUpdate();

    // For game display usage
    @SuppressWarnings("unused")
    public transient UnitView view;

    // Internal flag for simulation usage
    public transient boolean visited;

    private Wizard wizard; // The wizard this unit belongs to
    private float x;
    private float y;

    private final List<Ability> abilities = new ArrayList<>();

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
        for (Ability ability : abilities) {
            if (ability.getClass() == abilityClass) {
                T result = (T) ability;
                result.addStack();
                return result;
            }
        }
        return addAbilityByClass(abilityClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends StackableByOriginAbility> T addAbilityStack(Object origin, Class<T> abilityClass) {
        T result = null;
        for (Ability ability : abilities) {
            if (ability.getClass() == abilityClass) {
                StackableByOriginAbility stackableByOriginAbility = (StackableByOriginAbility) ability;
                if (stackableByOriginAbility.getOrigin() == origin) {
                    result = (T) ability;
                    break;
                }
            }
        }

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

    @SuppressWarnings("unchecked")
    public <A extends Ability> A getAbility(Class<A> abilityClass) {
        for (Ability ability : abilities) {
            if (abilityClass.isAssignableFrom(ability.getClass())) {
                return (A) ability;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <A extends Ability> A getAbility(Class<A> abilityClass, Object origin) {
        for (Ability ability : abilities) {
            if (abilityClass.isAssignableFrom(ability.getClass()) && ability.getOrigin() == origin) {
                return (A) ability;
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

    @SuppressWarnings("unused") // Used by client
    public abstract String getModelId();

    public void dispose() {
        ABILITIES_TO_DISPOSE.clear();
        ABILITIES_TO_DISPOSE.addAll(abilities);

        for (Ability ability : ABILITIES_TO_DISPOSE) {
            if (ability.getUnit() != null) {
                ability.dispose();
            }
        }

        abilities.clear();
        ABILITIES_TO_DISPOSE.clear();
    }
}
