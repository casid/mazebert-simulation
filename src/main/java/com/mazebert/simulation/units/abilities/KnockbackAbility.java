package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.KnockbackEffect;

public strictfp class KnockbackAbility extends ImmobilizeAbility {
    private float distance;

    @Override
    protected void immobilize(Creep target) {
        target.knockback(distance);

        KnockbackEffect knockbackEffect = target.addAbilityStack(KnockbackEffect.class);
        knockbackEffect.setDuration(0.4f * target.getSpeed());

        if (Sim.context().simulationListeners.areNotificationsEnabled()) {
            Sim.context().simulationListeners.showNotification(target, getKnockbackText(), getKnockbackColor());
        }
    }

    public int getKnockbackColor() {
        return 0xefefef;
    }

    public String getKnockbackText() {
        return "Knocked back!";
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Knockback";
    }

    @Override
    public String getDescription() {
        return "Each attack has a " + format.percent(getChance()) + "% chance to knock back the creep " + format.distance(distance) + ".";
    }
}
