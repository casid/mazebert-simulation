package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnExperienceChangedListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class BeaconAura extends AuraAbility<Beacon, Tower> implements OnExperienceChangedListener {

    private final float tribute = 0.25f;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    public BeaconAura() {
        super(CardCategory.Tower, Tower.class);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.onExperienceChanged.add(this);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.onExperienceChanged.remove(this);
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit.getElement() == Element.Light && !(unit instanceof Beacon);
    }

    @Override
    public void onExperienceChanged(Unit unit, float oldExperience, float newExperience) {
        if (newExperience > oldExperience) {
            float tribute = calculateTribute(oldExperience, newExperience);

            experienceSystem.grantExperience(getUnit(), tribute);
            experienceSystem.grantExperience((Tower) unit, -tribute);
        }
    }

    private float calculateTribute(float oldExperience, float newExperience) {
        float experienceGained = newExperience - oldExperience;
        float tribute = this.tribute * experienceGained;

        if (tribute > experienceGained) {
            return experienceGained;
        } else {
            return tribute;
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "United Knowledge";
    }

    @Override
    public String getDescription() {
        return "When " + format.element(Element.Light) + " towers in range gain xp, they gift " + format.percent(tribute) + "% of it to the Beacon.";
    }

    @Override
    public String getIconFile() {
        return "0070_starfall_512";
    }
}
