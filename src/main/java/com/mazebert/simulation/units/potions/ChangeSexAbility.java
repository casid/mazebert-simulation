package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ChangeSexAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.setGender(getNewGender(unit.getGender()));
    }

    private Gender getNewGender(Gender previous) {
        switch (previous) {
            case Male:
                return Gender.Female;
            case Female:
                return Gender.Male;
        }

        if (Sim.context().randomPlugin.getFloatAbs() < 0.5f) {
            return Gender.Female;
        } else {
            return Gender.Male;
        }
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Sex roulette!";
    }

    @Override
    public String getDescription() {
        return "Female becomes male\nMale becomes female\nNo sex becomes female or male randomly";
    }
}
