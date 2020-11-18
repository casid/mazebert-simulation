package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class NecronomiconAbility extends Ability<Tower> {
    private static final float experience = 0.3f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(experience * unit.getEldritchCardModifier());
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addExperienceModifier(-experience * unit.getEldritchCardModifier());
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(experience) + " experience.";
    }
}
