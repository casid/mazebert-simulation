package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class HerbWitchWisdom extends Ability<Tower> {
    private static final float experienceBonus = 0.2f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(experienceBonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addExperienceModifier(-experienceBonus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Wisdom of the Forest";
    }

    @Override
    public String getDescription() {
        return "Herb Witch gains " + formatPlugin.percent(experienceBonus) + "% more experience.";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }
}
