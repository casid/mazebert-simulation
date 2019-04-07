package com.mazebert.simulation.units.wizards;

public strictfp class WizardExperiencePower extends WizardPower {
    private static final float bonus = 0.02f;

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.experienceModifier += bonus * getSkillLevel();
    }

    @Override
    public int getRequiredLevel() {
        return 18;
    }

    @Override
    public String getTitle() {
        return "Archmage";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " wizard experience";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }
}
