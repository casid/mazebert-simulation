package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.potions.MeadAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class VikingAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        updateVikingAbilities(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);
        updateVikingAbilities(unit);
    }

    private void updateVikingAbilities(Tower unit) {
        MeadAbility meadAbility = unit.getAbility(MeadAbility.class);
        if (meadAbility != null) {
            meadAbility.update();
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Viking Wannabe";
    }

    @Override
    public String getDescription() {
        return "The carrier becomes a Viking.";
    }
}
