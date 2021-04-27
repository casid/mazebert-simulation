package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.listeners.OnExperienceChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class BeaconAura extends AuraAbility<Beacon, Tower> implements OnExperienceChangedListener {
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
        return unit.getElement() == Element.Light && !(unit instanceof Beacon); // TODO test (two players with both beacon!)
    }

    @Override
    public void onExperienceChanged(Unit unit, float oldExperience, float newExperience) {
        // TODO xp "tribute" to beacon...
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
        return "When " + format.element(Element.Light) + " towers in range gain xp, they gift 25% of it to the Beacon.";
    }

    @Override
    public String getIconFile() {
        return "0070_starfall_512";
    }
}
