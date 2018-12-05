package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ElvisAuraEffect extends StackableByOriginAbility<Creep> implements OnUpdateListener {
    private float duration;
    private float currentModifier;

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
        addEffect(0.5f);
    }

    @Override
    protected void dispose(Creep unit) {
        removeEffect();
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        duration -= dt;
        if (duration <= 0) {
            removeEffect();
            addEffect(1.25f);

            getUnit().onUpdate.remove(this);
        }
    }

    private void addEffect(float modifier) {
        currentModifier = modifier;
        getUnit().setSpeedModifier(getUnit().getSpeedModifier() * currentModifier);
    }

    private void removeEffect() {
        getUnit().setSpeedModifier(getUnit().getSpeedModifier() / currentModifier);
    }
}
