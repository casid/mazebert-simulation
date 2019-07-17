package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;

public strictfp class StunAbility extends ImmobilizeAbility {
    private float duration;

    @Override
    protected void immobilize(Creep target) {
        StunEffect stunEffect = target.addAbilityStack(StunEffect.class);
        stunEffect.setDuration(duration);

        if (Sim.context().simulationListeners.areNotificationsEnabled()) {
            Sim.context().simulationListeners.showNotification(target, getStunText(), getStunColor());
        }
    }

    public int getStunColor() {
        return 0xefefef;
    }

    public String getStunText() {
        return "Stunned!";
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Stun";
    }

    @Override
    public String getDescription() {
        return "Each attack has a " + format.percent(getChance()) + "% chance to stun the creep for " + duration + "s.";
    }
}
