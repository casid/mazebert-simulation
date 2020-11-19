package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Sim;

public strictfp class CultistPower extends WizardPower {
    private static final float bonus = 0.02f;

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);

        Sim.context().waveGateway.addCultistChance(getBonus());
    }

    @Override
    public int getRequiredLevel() {
        return 7;
    }

    @Override
    public String getTitle() {
        return "Eldritch Ritual";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(getBonus()) + " chance to spawn eldritch cultists.";
    }

    @Override
    public String getIconFile() {
        return "cultists";
    }

    private float getBonus() {
        return bonus * getSkillLevel();
    }
}
