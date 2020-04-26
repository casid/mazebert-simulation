package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnGoldChangedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class SatelliteDamage extends Ability<Tower> implements OnUnitAddedListener, OnGoldChangedListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        getUnit().getWizard().onGoldChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        getUnit().getWizard().onGoldChanged.add(this);
        updateDamage();
    }

    @Override
    public void onGoldChanged(Wizard wizard, long oldGold, long newGold) {
        updateDamage();
    }

    private void updateDamage() {
        getUnit().setBaseDamage(1); // Satellite will calculate base damage itself
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Money-fueled Lasers";
    }

    @Override
    public String getDescription() {
        return "The satellite shoots a laser beam at creeps within range. The more " + getCurrency().pluralLowercase + " you have, the more damage the laser deals.";
    }

    @Override
    public String getIconFile() {
        return "laser_beam_512";
    }
}
