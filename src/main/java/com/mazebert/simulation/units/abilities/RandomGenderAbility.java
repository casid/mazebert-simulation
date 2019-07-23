package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class RandomGenderAbility extends Ability<Tower> implements OnUnitAddedListener {

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.onUnitAdded.remove(this);

        if (Sim.context().randomPlugin.getFloatAbs() < 0.5f) {
            getUnit().setGender(Gender.Female);
        } else {
            getUnit().setGender(Gender.Male);
        }
    }
}
