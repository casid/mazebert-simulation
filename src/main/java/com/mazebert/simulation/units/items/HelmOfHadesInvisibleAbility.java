package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HelmOfHadesInvisibleAbility extends Ability<Tower> {

    private final HelmOfHades helmOfHades;

    public HelmOfHadesInvisibleAbility(HelmOfHades helmOfHades) {
        this.helmOfHades = helmOfHades;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        helmOfHades.changeName(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        helmOfHades.resetName();
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Invisibility";
    }

    @Override
    public String getLevelBonus() {
        return "The carrier becomes invisible.";
    }
}
