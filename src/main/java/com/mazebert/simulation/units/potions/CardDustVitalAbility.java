package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CardDustVitalAbility extends Ability<Tower> {
    private static final float health = 0.2f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        Sim.context().gameGateway.getGame().health += health;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Vital Dust";
    }

    @Override
    public String getDescription() {
        return "+ " + formatPlugin.percent(health) + "% player health";
    }
}
