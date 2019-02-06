package com.mazebert.simulation.units.wizards;

public strictfp class ProtectorPower extends WizardPower {
    private static final float bonus = 0.1f;

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.addHealth(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 24;
    }

    @Override
    public String getTitle() {
        return "Protector";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " health\nfor your wizard";
    }

    @Override
    public String getIconFile() {
        return "0038_shield_512";
    }
}
