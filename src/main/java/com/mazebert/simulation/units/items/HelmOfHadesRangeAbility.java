package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HelmOfHadesRangeAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addRange(1);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addRange(-1);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "True sight";
    }

    @Override
    public String getDescription() {
        return "The carrier's range is increased by 1.";
    }
}
