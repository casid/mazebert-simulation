package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.abilities.Ability;

public abstract strictfp class WizardPower extends Ability<Wizard> {

    private int skillLevel;

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getMaxSkillLevel() {
        return 10;
    }

    public int getRequiredLevel() {
        return 1;
    }
}
