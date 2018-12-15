package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public interface OnExperienceChangedListener {
    void onExperienceChanged(Unit unit, float oldExperience, float newExperience);
}
