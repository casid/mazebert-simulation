package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WeddingRingAbility extends Ability<Tower> {
    private final WeddingRingSystem system = Sim.context().weddingRingSystem;

    private final int index;

    public WeddingRingAbility(int index) {
        this.index = index;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        system.setTower(index, unit);
    }

    @Override
    protected void dispose(Tower unit) {
        system.setTower(index, null);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bond of Marriage";
    }

    @Override
    public String getDescription() {
        return "Whenever the carrier of this ring drinks a potion, the carrier of the other ring receives the effect of that potion, too.";
    }

    @Override
    public String getLevelBonus() {
        return "It takes " + format.seconds(WeddingRingSystem.SECONDS_FOR_MARRIAGE) + " to establish the bond of marriage.";
    }
}