package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class UnicornExperienceAbility extends Ability<Tower> implements OnRoundStartedListener, OnUnitAddedListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        Sim.context().simulationListeners.onRoundStarted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.onUnitAdded.remove(this);
        Sim.context().simulationListeners.onRoundStarted.add(this);
    }

    @Override
    public void onRoundStarted(Wave wave) {
        if (getUnit().getLevel() < Balancing.MAX_TOWER_LEVEL) {
            float xp = Balancing.getTowerExperienceForLevel(getUnit().getLevel() + 1);
            getUnit().setExperience(xp + 1.0f);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Preservation";
    }

    @Override
    public String getDescription() {
        return "Every survived round the unicorn gains +1 level.";
    }

    @Override
    public String getIconFile() {
        return "0070_starfall_512";
    }
}
